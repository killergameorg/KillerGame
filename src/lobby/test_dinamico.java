package lobby;

import lobby.lobbyController.LobbyController;
import lobby.lobbyModel.LobbyModel;
import lobby.lobbyView.LobbyView;
import maincontroller.gameinfo.Account;

import java.lang.reflect.Field;

public class test_dinamico {
    private int atributo1;
    private int atributo2;
    private EnumTipo atributoEnum;

    public void cambiarValor(int numeroSeleccionado, Object nuevoValor) {
        try {
            Field[] campos = this.getClass().getDeclaredFields();
            int cantidadCambiables = 0;
            for (Field campo : campos) {
                if (campo.getType() == int.class || campo.getType().isEnum()) {
                    cantidadCambiables++;
                }
            }
        
            if (numeroSeleccionado > cantidadCambiables) {
                System.out.println("Número seleccionado inválido");
                return;
            }

            campos[numeroSeleccionado].setAccessible(true);
            if (campos[numeroSeleccionado].getType() == int.class) {
                campos[numeroSeleccionado].set(this, (int) nuevoValor);
            } else {
                campos[numeroSeleccionado].set(this, nuevoValor);
            }

            System.out.println("Valor de " + campos[numeroSeleccionado].getName() + " cambiado a " + nuevoValor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // LobbyController lobbyController = new LobbyController();
        // LobbyView lobbyView = new LobbyView(lobbyController);
        // lobbyController.setLobbyView(lobbyView);
        // LobbyModel lobbyModel = new LobbyModel(lobbyController);
        // lobbyController.setLobbyModel(lobbyModel);
        // lobbyModel.minusGameRuleValue();
        // System.out.println(lobbyModel.getDinamicRules().toString());
    }

}

enum EnumTipo {
    TIPO1,
    TIPO2
}
