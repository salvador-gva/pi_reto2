package org.proyecto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Aplicacion {

    public static void main(String[] args) throws IOException {

            //web
            String url = "https://vandal.elespanol.com/juegos/0/videojuegos";
            Document pagina = Jsoup.connect(url).timeout(15000).get();

            //Crear arraylist
            List<String> videojuegos = new ArrayList<>();

            //Recorrer los elementos de ls web
            for (Element juego : pagina.select("table.tablestriped4 table.table")) {
                Element titulo = juego.selectFirst("a[href^=/juegos/] strong");
                Element descripcion = juego.selectFirst("td p");
                Element genero = juego.selectFirst("div.mt05 p");

                //leer elementos del ArrayList
                videojuegos.add(
                        "Título: " + titulo.text() + "\n"
                                + "Enlace: " + titulo.parent().absUrl("href") + "\n"
                                + "Descripción: " + (descripcion == null ? "No disponible" : descripcion.text()) + "\n"
                                + "Género: " + (genero == null ? "No disponible" : genero.text()) + "\n"
                );
            }

            //Crear el ficheroCatalogo
            Path ficheroCatalogo = Path.of("catalogo.txt");
            Files.write(ficheroCatalogo, videojuegos, StandardCharsets.UTF_8);

            //Recorrer y mostrar el ficheroCatalogo
            for (String lista : Files.readAllLines(ficheroCatalogo, StandardCharsets.UTF_8)) {
                System.out.println(lista);
            }
            System.out.println("Videojuegos encontrados: " + videojuegos.size());
        }
}

