package es.ujaen.ssmmaa.agentes;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Agente secundario que realiza una operación matemática y envía el resultado al AgentePrincipal.
 */
public class AgenteSecundario extends Agent {

    private int num1, num2;
    private String nombreOperacion;
    private String nombreAgentePrincipal;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length == 5) {
            num1 = (int) args[0];
            num2 = (int) args[1];
            nombreOperacion = (String) args[2];
            String nombreComportamiento = (String) args[3];
            nombreAgentePrincipal = (String) args[4];

            // Agregar comportamiento OneShotBehaviour
            if (nombreComportamiento.equals("OneShot")) {
                addBehaviour(new OperacionOneShotBehaviour());
            } else {
                System.out.println("Comportamiento no reconocido.");
                doDelete();
            }

        } else {
            System.out.println("Error: argumentos incorrectos.");
            doDelete();
        }
    }

    double operacion(int num1, int num2, String operacion) {
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

            // Enviar mensaje con el resultado
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.addReceiver(new AID(nombreAgentePrincipal, AID.ISLOCALNAME));
            mensaje.setContent("Resultado de " + num1 + " " + nombreOperacion + " " + num2 + " = " + resultado);
            send(mensaje);

            System.out.println("AgenteSecundario envió resultado a " + nombreAgentePrincipal);
            doDelete(); // Termina el agente después de enviar el resultado
        }
    }
}
