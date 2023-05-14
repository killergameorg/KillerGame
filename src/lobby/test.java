package lobby;

import java.lang.reflect.Field;

import lobby.lobbyModel.LobbyModel;
import maincontroller.Account;

public class test {
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
        LobbyModel lobbyModel = new LobbyModel(null);
        Account a = new Account(null, null);
        lobbyModel.addAccount(a);
        lobbyModel.beforeGameRulePosition();
        lobbyModel.nextGameRulePosition();
        lobbyModel.nextGameRulePosition();
        lobbyModel.nextGameRulePosition();
    }

}

enum EnumTipo {
    TIPO1,
    TIPO2
}