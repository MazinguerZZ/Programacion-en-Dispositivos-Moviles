package com.pmm.menu_aula;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pmm.menu_aula.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lista;
    private List<String> ejemplosLista;
    private ArrayAdapter<String> adaptadorEjemplo;

    private ItemFragment itemFragment;

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


        lista= (ListView) findViewById(R.id.ListViewEjemplo);
        ejemplosLista= new ArrayList<>();
        ejemplosLista.add("Ana");
        ejemplosLista.add("Sergio");
        ejemplosLista.add("Pedro");
        ejemplosLista.add("María");
        ejemplosLista.add("Juana");
        ejemplosLista.add("Lucas");
        adaptadorEjemplo= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ejemplosLista);
        lista.setAdapter(adaptadorEjemplo);
        lista.setOnItemClickListener(this);

        if(savedInstanceState == null) {

            itemFragment= new ItemFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, itemFragment)
                    .commit();

            for(int i= 1; i<=2; i++) {
                PlaceholderContent.ITEMS.add(new PlaceholderContent.PlaceholderItem(String.valueOf(i), "Artículo "+i, "detalle"));

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);

        MenuItem searchItem= menu.findItem(R.id.app_bar_search);
        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Buscando: " + query, Toast.LENGTH_LONG).show();
                searchView.clearFocus();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(id == R.id.Opcion_menu1) {
            Toast.makeText(this, "Pulsada opción 1", Toast.LENGTH_SHORT).show();

            ejemplosLista.add("Ricardo");
            adaptadorEjemplo.notifyDataSetChanged();

            PlaceholderContent.ITEMS.clear();
            PlaceholderContent.ITEMS.add(new PlaceholderContent.PlaceholderItem(String.valueOf(1), "Madrid", "detalle"));
            itemFragment.updatedRecyclerViewAdapter();

        }
        else if(id == R.id.Opcion_menu2) {
            Toast.makeText(this, "Pulsada opción 2", Toast.LENGTH_SHORT).show();

            ejemplosLista.clear();
            adaptadorEjemplo.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "El ejemplo seleccionado es: " + ejemplosLista.get(position), Toast.LENGTH_LONG).show();
    }
}