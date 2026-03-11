package com.pmm.http;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pmm.http.databinding.FragmentHomeBinding;
import com.pmm.http.posts.Post;
import com.pmm.http.posts.PostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private PostAdapter adapter;
    private List<Post> postList= new ArrayList<>();
    private final String URL_JSON_POST= "https://jsonplaceholder.typicode.com/posts";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentHomeBinding.inflate(inflater, container, false);

        adapter= new PostAdapter(postList);
        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPosts.setAdapter(adapter);


        // Botón para cargar datos (PARA EL GET)
        binding.buttonGet.setOnClickListener(v -> sendGetRequest());
        binding.buttonPost.setOnClickListener(v -> sendPostRequest());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void sendPostRequest() {
        new Thread(() -> {
            BufferedReader reader= null;
            try{
                // Establecemos la conexión y preparamos la solicitud tipo GET
                URL url = new URL(URL_JSON_POST);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                String jsonBody= "{ \"userId\": 99, \"Id\": 1, \"title\": \"Nuevo título\", \"body\": \"Contenido del post\"}";

                conn.connect(); // Apertura explícita
                System.out.println("Conexión ABIERTA con URL: " + URL_JSON_POST);

                try (OutputStream os= conn.getOutputStream()) {
                    byte[] input= jsonBody.getBytes("UTF-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();  // Llama implícitamente al .connect()

                if(responseCode == HttpURLConnection.HTTP_CREATED) {
                    System.out.println("Creación correcta del objeto por el servidor");
                    reader= new BufferedReader(new InputStreamReader((conn.getInputStream())));
                }
                else {
                    reader= new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                }

                StringBuilder response= new StringBuilder();
                String line;
                response.append("[");
                while((line= reader.readLine()) != null) {
                    response.append(line);
                }
                response.append("]");

                List<Post> fetchedPosts= new ArrayList<>();
                JSONArray jsonArray= new JSONArray(response.toString());
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject obj= jsonArray.getJSONObject(i);
                    Post post= new Post(
                            obj.getInt("userId"),
                            obj.getInt("id"),
                            obj.getString("title"),
                            obj.getString("body")
                    );
                    fetchedPosts.add(post);
                }

                requireActivity().runOnUiThread(() -> adapter.setPost(fetchedPosts));

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /**
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button botonGet= view.findViewById(R.id.buttonGet);
        botonGet.setOnClickListener(v->sendGetRequest());
    }
*/
    private void sendGetRequest() {
        new Thread(() -> {
           List<Post> fetchedPosts= new ArrayList<>();
           BufferedReader reader= null;

           try{
               // Establecemos la conexión y preparamos la solicitud tipo GET
               URL url = new URL(URL_JSON_POST);
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("GET");
               conn.setConnectTimeout(5000);
               conn.setReadTimeout(5000);

               conn.connect(); // Apertura explícita
               System.out.println("Conexión ABIERTA con URL: " + URL_JSON_POST);

               int responseCode = conn.getResponseCode();  // Llama implícitamente al .connect()
               // si no lo hubiésemos hecho antes.

               if(responseCode == HttpURLConnection.HTTP_OK) {
                   System.out.println("Conexión ESTABLECIDA OK.");

                   reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
                   StringBuilder response= new StringBuilder();
                   String line;
                   while((line= reader.readLine()) != null) {
                       response.append(line);
                   }

                   JSONArray jsonArray= new JSONArray(response.toString());
                   for(int i=0; i<jsonArray.length(); i++) {
                       JSONObject obj= jsonArray.getJSONObject(i);
                       Post post= new Post(
                               obj.getInt("userId"),
                               obj.getInt("id"),
                               obj.getString("title"),
                               obj.getString("body")
                       );
                       fetchedPosts.add(post);
                   }
               }

               requireActivity().runOnUiThread(() -> adapter.setPost(fetchedPosts));

           } catch (MalformedURLException e) {
               throw new RuntimeException(e);
           } catch (IOException e) {
               throw new RuntimeException(e);
           } catch (JSONException e) {
               throw new RuntimeException(e);
           }
        }).start();
    }
}