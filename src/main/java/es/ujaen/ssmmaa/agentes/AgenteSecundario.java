package es.ujaen.ssmmaa.agentes;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Agente secundario que realiza una operación matemática y envía el resultado al AgentePrincipal.
 */
public class AgenteSecundario extends Agent {

    private int num1, num2;
    private String nombreOperacion;
    private String nombreAgentePrincipal;
    long tiempoInicio;
    long tiempoFin;

    @Override
    protected void setup() {
        // Calculamos el tiempo de ejecución para mandar el tiempo de operación a AgentePrincipal
        tiempoInicio = System.currentTimeMillis();
        Object[] args = getArguments();
        if (args != null && args.length == 6) {
            try {
                num1 = Integer.parseInt(args[0].toString());
                num2 = Integer.parseInt(args[1].toString());
                nombreOperacion = args[2].toString().toLowerCase(); // Evita problemas de capitalización
                String nombreComportamiento = args[3].toString();
                int ciclos = Integer.parseInt(args[4].toString());
                System.out.println("Ciclos a ejecutar: " + ciclos);
                nombreAgentePrincipal = args[5].toString();

                // Agregar comportamiento OneShotBehaviour o CyclicBehaviour según corresponda
                if (nombreComportamiento.equalsIgnoreCase("OneShot")) {
                    addBehaviour(new OperacionOneShotBehaviour());
                } else {
                    addBehaviour(new OperacionCyclicBehaviour(ciclos));
                }
            } catch (Exception e) {
                System.out.println("Error al procesar argumentos: " + e.getMessage());
                doDelete();
            }
        } else {
            System.out.println("Error: argumentos incorrectos.");
            doDelete();
        }
    }

    private double operacion(int num1, int num2, String operacion) {
        switch (operacion) {
            case "suma":
                return num1 + num2;
            case "resta":
                return num1 - num2;
            case "multiplicacion":
                return num1 * num2;
            case "division":
                return num2 != 0 ? (double) num1 / num2 : Double.NaN;
            default:
                System.out.println("Operación no reconocida: " + operacion);
                return 0;
        }
    }

    /**
     * Comportamiento OneShotBehaviour que ejecuta la operación y envía el resultado.
     */
    private class OperacionOneShotBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            double resultado = operacion(num1, num2, nombreOperacion);
            tiempoFin = System.currentTimeMillis();

            // Enviar mensaje con el resultado y el tiempo de ejecución
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.addReceiver(new AID(nombreAgentePrincipal, AID.ISLOCALNAME));
            mensaje.setContent("Resultado de " + num1 + " " + nombreOperacion + " " + num2 + " = " + resultado
                    + " en " + (tiempoFin - tiempoInicio) + " ms");
            send(mensaje);

            System.out.println("AgenteSecundario envió resultado a " + nombreAgentePrincipal);
            doDelete(); // Termina el agente después de enviar el resultado
        }
    }

    /**
     * Comportamiento CyclicBehaviour que ejecuta la operación y envía el resultado.
     */

    private class OperacionCyclicBehaviour extends CyclicBehaviour {
        private int ciclos;

        public OperacionCyclicBehaviour(int ciclos) {
            this.ciclos = ciclos;
        }

        @Override
        public void action() {
            if (ciclos > 0) {
                double resultado = operacion(num1, num2, nombreOperacion);
                tiempoFin = System.currentTimeMillis();

                // Enviar mensaje con el resultado y el tiempo de ejecución
                ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
                mensaje.addReceiver(new AID(nombreAgentePrincipal, AID.ISLOCALNAME));
                mensaje.setContent("Resultado de " + num1 + " " + nombreOperacion + " " + num2 + " = " + resultado
                        + " en " + (tiempoFin - tiempoInicio) + " ms");
                send(mensaje);

                System.out.println("AgenteSecundario envió resultado a " + nombreAgentePrincipal);
                ciclos--;

                // Pausa entre ejecuciones para evitar sobrecarga
                block(1000); // Bloquea el comportamiento por 1 segundo
            } else {
                doDelete(); // Elimina el agente cuando haya completado los ciclos
            }
        }
    }

}
