// Adrián López POO Lab 3

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
     // Listas para almacenar productos, bebidas y snacks.
    private static List<Producto> productos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Definir las listas para productos, bebidas y snacks.
        List<Producto> productos = new ArrayList<>();
        List<Bebidas> bebidas = new ArrayList<>();
        List<Snacks> snacks = new ArrayList<>();

        // Ruta del archivo CSV que contiene los productos.
        String csvFilePath = "products.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            // Leer la primera línea (encabezados) y descartarla
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Dividir la línea en datos utilizando comas.
                String[] data = line.split(",");

                // Extraer y convertir los datos de los productos.
                int id = Integer.parseInt(data[0].replace("\"", "").trim());
                String nombre = data[1].replace("\"", "").trim();
                int cantidadDisponible = Integer.parseInt(data[2].replace("\"", "").trim());
                int cantidadVendidos = Integer.parseInt(data[3].replace("\"", "").trim());
                boolean estado = Boolean.parseBoolean(data[4].replace("\"", "").trim());
                double precio = Double.parseDouble(data[5].replace("\"", "").trim());

                String categoria = data[6].replace("\"", "").trim();
                if (categoria.equals("Bebida")) {
                    int ml = data[7].isEmpty() ? 0 : Integer.parseInt(data[7].replace("\"", "").trim());
                    String tipo = data[8].replace("\"", "").trim();
                    // Crear y almacenar una bebida en la lista de bebidas.
                    Bebidas bebida = new Bebidas(id, nombre, cantidadDisponible, cantidadVendidos, estado, precio, ml, tipo);
                    bebidas.add(bebida);
                } else if (categoria.equals("Snack")) {
                    int gramos = data[9].isEmpty() ? 0 : Integer.parseInt(data[9].replace("\"", "").trim());
                    String sabor = data[10].replace("\"", "").trim();
                    String tamaño = data[11].replace("\"", "").trim();
                    // Crear y almacenar un snack en la lista de snacks.
                    Snacks snack = new Snacks(id, nombre, cantidadDisponible, cantidadVendidos, estado, precio, gramos, sabor, tamaño);
                    snacks.add(snack);
                }

                // Crear y almacenar un producto en la lista de productos.
                Producto producto = new Producto(id, nombre, cantidadDisponible, cantidadVendidos, estado, precio);
                productos.add(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        // Menú de usuario
        while (true) {
            System.out.println("Menu de usuario:");
            System.out.println("1. Buscar producto por ID");
            System.out.println("2. Listar productos de una categoría");
            System.out.println("3. Mostrar ventas actuales y comisión");
            System.out.println("4. Ver informe");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    buscarProductoPorId();
                    break;
                case 2:
                    listarProductosDeCategoria();
                    break;
                case 3:
                    mostrarVentasYComision();
                    break;
                case 4:
                    verInforme();
                    break;
                case 5:
                    System.out.println("Saliendo del programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
                    break;
            }
        }
    }
    private static void buscarProductoPorId() {
        System.out.print("Ingrese el ID del producto que desea buscar: ");
        int idBuscado = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
    
        // Buscar el producto por su ID utilizando Stream y Optional.
        Optional<Producto> productoEncontrado = productos.stream()
                .filter(producto -> producto.getId() == idBuscado)
                .findFirst();
    
        if (productoEncontrado.isPresent()) {
            System.out.println("Producto encontrado:");
            System.out.println(productoEncontrado.get());
        } else {
            System.out.println("No se encontró un producto con el ID proporcionado.");
        }
    }
    
    private static void listarProductosDeCategoria() {
        System.out.print("Ingrese la categoría que desea listar (Bebida o Snack): ");
        String categoriaDeseada = scanner.nextLine();
    
        // Filtrar productos por categoría y almacenarlos en una lista.
        List<Producto> productosCategoria = productos.stream()
                .filter(producto -> producto.getCategoria().equalsIgnoreCase(categoriaDeseada))
                .collect(Collectors.toList());
    
        if (productosCategoria.isEmpty()) {
            System.out.println("No se encontraron productos en la categoría proporcionada.");
        } else {
            System.out.println("Productos en la categoría " + categoriaDeseada + ":");
            productosCategoria.forEach(producto -> System.out.println(producto.getNombre()));
        }
    }
    
    private static void mostrarVentasYComision() {
        // Calcular las ventas totales sumando las ventas de todos los productos.
        double totalVentas = productos.stream().mapToDouble(Producto::getVentas).sum();
    
        System.out.println("Ventas totales: Q" + totalVentas);
    
        // Calcular y mostrar la comisión por categoría.
        Map<String, Double> comisionPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoria, Collectors.summingDouble(Producto::getComision)));
    
        System.out.println("Porcentaje por categoría:");
        comisionPorCategoria.forEach((categoria, comision) -> {
            double porcentaje = (comision / totalVentas) * 100;
            System.out.println(categoria + ": Q" + comision + " (" + porcentaje + "%)");
        });
    }
    
    // Opción para informe
    private static void verInforme() {
        System.out.println("Informe:");
        listarCategoriasConTotalDeProductos();
        listarProductosPorCategoria();
        mostrarTotalDeVentasYComision();
    }

    private static void listarCategoriasConTotalDeProductos() {
        // Argupacion de productos por categoría y contarlos.
        Map<String, Long> categoriasConTotal = productos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoria, Collectors.counting()));

        System.out.println("1. Listado de categorías con el total de productos:");
        for (Map.Entry<String, Long> entry : categoriasConTotal.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    private static void listarProductosPorCategoria() {
        System.out.println("2. Listado de productos por categoría:");
        // Agrupacion de productos por categoría y se muestran los nombres de los productos en cada categoría.
        Map<String, List<Producto>> productosPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoria));

        for (Map.Entry<String, List<Producto>> entry : productosPorCategoria.entrySet()) {
            System.out.println(entry.getKey() + ":");
            entry.getValue().forEach(producto -> System.out.println(producto.getNombre()));
        }
    }

    private static void mostrarTotalDeVentasYComision() {
        // Calcular nuevamente las ventas totales.
        double totalVentas = productos.stream().mapToDouble(Producto::getVentas).sum();

        System.out.println("3. Total de ventas:");
        System.out.println("a. Ventas totales Q" + totalVentas);

        System.out.println("b. Porcentaje por categoría:");
        // Calcular y mostrar la comisión por categoría.
        Map<String, Double> comisionPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoria, Collectors.summingDouble(Producto::getComision)));

        comisionPorCategoria.forEach((categoria, comision) -> {
            System.out.println(categoria + " : Q" + comision);
        });
    }
}