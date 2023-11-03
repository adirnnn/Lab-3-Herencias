// Adrián López POO Lab 3

public class Snacks extends Producto {
    private int gramos;
    private String sabor;
    private String tamaño;

    // Constructor para inicializar un producto de tipo Snacks con sus atributos específicos.
    public Snacks(int id, String nombre, int cantidadDisponible, int cantidadVendidos, boolean estado, double precio, int gramos, String sabor, String tamaño) {
        super(id, nombre, cantidadDisponible, cantidadVendidos, estado, precio);
        this.gramos = gramos;
        this.sabor = sabor;
        this.tamaño = tamaño;
    }

    // Método para calcular el valor total de venta de una cantidad específica de snacks.
    public double calcularValorVenta(int cantidad) {
        return cantidad * getPrecio();
    }

    // Método para registrar un nuevo sabor para los snacks.
    public void registrarNuevoSabor(String nuevoSabor) {
        sabor = nuevoSabor;
    }
}