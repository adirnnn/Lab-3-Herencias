// Adrián López POO Lab 3

public class Producto {
    private int id;
    private String nombre;
    private int cantidadDisponible;
    private int cantidadVendidos;
    private boolean estado;
    private double precio;
    private String categoria;

    // Constructor para inicializar un producto con sus atributos principales.
    public Producto(int id, String nombre, int cantidadDisponible, int cantidadVendidos, boolean estado, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadVendidos = cantidadVendidos;
        this.estado = estado;
        this.precio = precio;
    }

    // Método para obtener el ID del producto.
    public int getId() {
        return id;
    }

    // Método para obtener el nombre del producto.
    public String getNombre() {
        return nombre;
    }

    // Método para obtener la cantidad de unidades vendidas (ventas).
    public int getVentas() {
        return cantidadVendidos;
    }

    // Método para calcular y obtener la comisión, asumiendo que es el 20% de las ventas.
    public double getComision() {
        return 0.20 * getVentas();
    }

    // Método para obtener el precio del producto.
    public double getPrecio() {
        return precio;
    }

    // Método para obtener la categoría del producto.
    public String getCategoria() {
        return categoria;
    }

    // Método para registrar una venta y actualizar las cantidades disponibles y vendidas.
    public void vender(int cantidad) {
        cantidadVendidos += cantidad;
        cantidadDisponible -= cantidad;
    }
}