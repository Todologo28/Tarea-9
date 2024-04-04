import utils.Pass;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newCachedThreadPool();
        boolean contraseñaValidada = false;

        System.out.println("\nIngrese una contraseña para validar con las siguientes características: " +
                "\n- Mínimo 8 caracteres" +
                "\n- Debe contener al menos una mayúscula, una minúscula, un número y un carácter especial\n");

        while (!contraseñaValidada) {
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();
            if (password.equalsIgnoreCase("Salir")) {
                break;
            }
            Future<Boolean> resultado = executor.submit(new Pass(password));
            try {
                if (resultado.get()) {
                    System.out.println("La contraseña '" + password + "' es válida.");
                    contraseñaValidada = true; // Establecer la bandera como verdadera si la contraseña es válida
                } else {
                    System.out.println("La contraseña '" + password + "' no es válida.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Verificación completada");
    }
}

