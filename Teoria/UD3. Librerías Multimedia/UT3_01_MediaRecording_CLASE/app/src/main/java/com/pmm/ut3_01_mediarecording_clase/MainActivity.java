package com.pmm.ut3_01_mediarecording_clase;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.FileOutputOptions;
import androidx.camera.video.Quality;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.video.VideoRecordEvent;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // Atributos para el audio
    private MediaRecorder grabadorAudio;
    private MediaPlayer reproductorAudio;
    private MaterialButton btnGrabarAudio, btnPlayAudio;
    private final static String audioFileName = "audio_file.mp4";

    private final static int CODIGO_PERMISO_AUDIO = 100;

    private boolean grabandoAudio = false;

    // Atributos para la imagen
    private MaterialButton btnCaptarImg;
    private ImageView imgCaptura;
    private final static int CODIGO_PERMISO_IMAGEN = 200;
    private final static String imageFileName = "image_file.jpeg";
    private Uri photoUri;
    private ActivityResultLauncher<Intent> cameraLauncher;

    // Atributos para el video
    private PreviewView previewView;
    private VideoView videoView;
    private VideoCapture<Recorder> videoGrabador;
    private Recording grabacionVideo;
    private ProcessCameraProvider cameraProvider;
    private final static String videoFileName= "video_file.mp4";
    private final static int CODIGO_PERMISO_VIDEO = 300;
    private MaterialButton btnActivarVideo, btnGrabarVideo;
    private boolean grabandoVideo, videoGrabado, reproduciendoVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnGrabarAudio = findViewById(R.id.btnGrabarAudio);
        btnGrabarAudio.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_mic_on));
        btnGrabarAudio.setOnClickListener(v->{
            new Thread(() -> {
                if (!grabandoAudio) {
                    int permisoGrabar = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
                    if (permisoGrabar == PackageManager.PERMISSION_GRANTED) {
                        iniciarGrabacionAudio();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, CODIGO_PERMISO_AUDIO);
                    }
                } else {
                    pararGrabacionAudio();
                }

            }).start();
        });

        // Reproducir Audio

        btnPlayAudio = findViewById(R.id.btnPlayAudio);
        btnPlayAudio.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_play));
        btnPlayAudio.setOnClickListener(v-> {
            new Thread(()-> {
                if (!grabandoAudio) {
                    reproducirAudio();
                }
            }).start();
        });

        // Reproducir Imagen

        btnCaptarImg = findViewById(R.id.btnCaptarImagen);
        btnCaptarImg.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_camera));
        btnCaptarImg.setOnClickListener(v -> {
            new Thread(() -> {
                int permisoImagen = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                if (permisoImagen == PackageManager.PERMISSION_GRANTED) {
                    capturarImagen();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CODIGO_PERMISO_IMAGEN);
                }
            }).start();
        });
        imgCaptura = findViewById(R.id.imgCaptura);
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imgCaptura.setImageURI(photoUri);
                        convertirImagen();
                    }

                }
        );

        // Reproducir video

        btnActivarVideo = findViewById(R.id.btnActivarVideo);
        btnActivarVideo.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_camera_video));
        btnActivarVideo.setOnClickListener(v-> {
            new Thread(() -> {
                if (!videoGrabado) {
                    int permisoGrabarAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
                    int permisoGrabarVideo = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (permisoGrabarAudio == PackageManager.PERMISSION_GRANTED && permisoGrabarVideo == PackageManager.PERMISSION_GRANTED) {
                        activarCamara();
                    } else {
                        requestPermissions(new String[]{
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.CAMERA
                        }, CODIGO_PERMISO_VIDEO);
                    }
                } else {
                    reproducirVideo();
                }
            }).start();
        });

        btnGrabarVideo = findViewById(R.id.btnGrabarVideo);
        btnGrabarVideo.setOnClickListener(v-> {
                if (!grabandoVideo) {
                    iniciarGrabacionVideo();
                }else {
                    pararGrabacionVideo();
                }
        });
        grabandoVideo = false;
        videoGrabado = false;

    }

    private void reproducirVideo() {
        runOnUiThread(() -> {
            cameraProvider.unbindAll();
            previewView.setVisibility(View.GONE);

            videoView = findViewById(R.id.videoView);
            videoView.setVisibility(View.VISIBLE);

            String ficheroVideo = getFilesDir() + "/" + videoFileName;
            videoView.setVideoURI(Uri.fromFile(new File(ficheroVideo)));
            videoView.start();
        });
    }

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    private void iniciarGrabacionVideo() {

        if (reproduciendoVideo){
            pararReproduccionVideo();
        }

        String ficheroVideo = getFilesDir() + "/" + videoFileName;
        File videoFile = new File(ficheroVideo);

        FileOutputOptions outputOptions = new FileOutputOptions.Builder(videoFile).build();

        grabacionVideo = videoGrabador.getOutput()
                .prepareRecording(this, outputOptions)
                .withAudioEnabled()
                .start(ContextCompat.getMainExecutor(this), event -> {
                    if (event instanceof VideoRecordEvent.Start) {
                        Log.d("VIDEO", "Grabacion iniciada");
                        grabandoVideo = true;
                    } else if (event instanceof VideoRecordEvent.Finalize) {
                        Log.d("VIDEO", "Grabacion finalizada");
                        grabandoVideo = false;
                    }
                });

        runOnUiThread(()-> {
            btnGrabarVideo.setText(R.string.parar_grabacion_video);
            btnGrabarVideo.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_stop));
        });
    }

    private void pararReproduccionVideo() {
        videoView.stopPlayback();
        runOnUiThread(()-> {
            previewView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
        });
        activarCamara();
    }


    private void pararGrabacionVideo() {
        if (grabacionVideo != null) {
            grabacionVideo.stop();
            grabacionVideo = null;
        }
        grabandoVideo = false;
        videoGrabado = true;

        runOnUiThread(() -> {
            btnGrabarVideo.setText(R.string.grabar_video);
            btnActivarVideo.setEnabled(true);
            btnActivarVideo.setText(R.string.reproducir_video);
            btnGrabarVideo.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_camera_active));
        });
    }

    private void activarCamara() {

        previewView = findViewById(R.id.previewViewVideo);
        Preview preview = new Preview.Builder().build();

        Recorder recorder = new Recorder.Builder().setQualitySelector(QualitySelector.from(Quality.HD)).build();
        videoGrabador = VideoCapture.withOutput(recorder);

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(()-> {
            try {
                cameraProvider = cameraProviderFuture.get();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, videoGrabador);
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, ContextCompat.getMainExecutor(this));

        runOnUiThread(() -> {
            btnActivarVideo.setEnabled(false);
            btnGrabarVideo.setEnabled(true);

            btnGrabarVideo.animate()
                    .alpha(1f)
                    .setDuration(1000)
                    .setStartDelay(100)
                    .start();

        });

    }


    private void convertirImagen() {

        File ficheroJpeg = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);
        File ficheroPng = new File(getFilesDir(),imageFileName.split("\\.")[0]+".png");

        Bitmap bitmap = BitmapFactory.decodeFile(ficheroJpeg.getAbsolutePath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(ficheroPng);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
                throw new RuntimeException(e);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void capturarImagen() {
        File storageDir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = new File(storageDir, imageFileName);

        photoUri = FileProvider.getUriForFile(
                this,
                getPackageName()+".fileprovider",
                photoFile
        );

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        cameraLauncher.launch(intent);


    }

    private void reproducirAudio() {
        reproductorAudio = new MediaPlayer();

        String ficheroAudio = getFilesDir() + "/" + audioFileName;
        try {
            reproductorAudio.setDataSource(ficheroAudio);
            reproductorAudio.prepare();
            reproductorAudio.start();

            runOnUiThread(()->{
                btnPlayAudio.setEnabled(false);
                Toast.makeText(this, "Reproduciendo fichero: " + ficheroAudio, Toast.LENGTH_SHORT).show();
            });
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo reproducir el fichero: " + ficheroAudio, Toast.LENGTH_SHORT).show();
        }

        reproductorAudio.setOnCompletionListener(mp -> {
            mp.release();
            reproductorAudio = null;

            runOnUiThread(() -> {
                btnPlayAudio.setEnabled(true);
            });
        });
    }

    private void iniciarGrabacionAudio() {
        grabadorAudio = new MediaRecorder();

        // Configuramos el origen, formato y codificacion del audio
        grabadorAudio.setAudioSource(MediaRecorder.AudioSource.MIC);
        grabadorAudio.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        grabadorAudio.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        // Preparamos el fichero para la grabación del audio
        String ficheroAudio= getFilesDir() + "/" + audioFileName;
        grabadorAudio.setOutputFile(ficheroAudio);
        try {
            grabadorAudio.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        grabadorAudio.start();
        grabandoAudio = true;

        runOnUiThread(() -> {
            btnGrabarAudio.setText(R.string.parar_grabacion_audio);
            btnGrabarAudio.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_mic_off));
        });

    }

    private void pararGrabacionAudio() {
        grabadorAudio.stop();
        grabadorAudio.release();
        grabadorAudio = null;
        grabandoAudio = false;

        runOnUiThread(() -> {
            btnGrabarAudio.setText(R.string.grabar_audio);
            btnGrabarAudio.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_mic_on));
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        if (requestCode == CODIGO_PERMISO_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso GRABACIÓN AUDIO CONCEDIDO.", Toast.LENGTH_SHORT).show();
                iniciarGrabacionAudio();
            } else {
                Toast.makeText(this, "Permiso GRABACIÓN AUDIO NO CONCEDIDO", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CODIGO_PERMISO_IMAGEN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso CAPTURA IMAGEN CONCEDIDO.", Toast.LENGTH_SHORT).show();
                capturarImagen();
            } else {
                Toast.makeText(this, "Permiso CAPTURAR IMAGEN NO CONCEDIDO", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CODIGO_PERMISO_VIDEO) {
            if (grantResults.length == 0) return;

            boolean cameraOK = false;
            boolean audioOK = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.CAMERA)) {
                    cameraOK = (grantResults[i] == PackageManager.PERMISSION_GRANTED);
                }
                if (permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
                    audioOK = (grantResults[i] == PackageManager.PERMISSION_GRANTED);
                }
            }

            if (cameraOK && audioOK) {
                activarCamara();
            } else
                Toast.makeText(this, "Permiso CAPTURA VIDEO DENEGADO.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        liberarRecursos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liberarRecursos();
    }

    private void liberarRecursos() {

        // Detener grabación de vídeo
        if (grabacionVideo != null) {
            grabacionVideo.stop();
            grabacionVideo = null;
        }

        // Liberar cámara
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            cameraProvider = null;
        }

        // Detener audio
        if (grabadorAudio != null) {
            try {
                grabadorAudio.stop();
            } catch (Exception ignored) {}
            grabadorAudio.release();
            grabadorAudio = null;
            grabandoAudio = false;
        }

        // Detener reproducción de audio
        if (reproductorAudio != null) {
            reproductorAudio.release();
            reproductorAudio = null;
        }
    }

}