package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.controller.dto.BuyResponse;

public class BuyResultView {
    private final MessageView messages;

    public BuyResultView(MessageView messages) {
        this.messages = messages;
    }

    public void show(BuyResponse r) {
        switch (r.getStatus()) {
        case SUCCESS ->
                messages.showMessage("[+] " + r.getItem().getName() + " ya está en tu equipo!");
        case NOT_FOUND ->
                messages.showMessage("[!] Ese objeto (" + r.getRequestedId() + ") no existe en nuestra tienda.");
        case NOT_ENOUGH_GOLD ->
                messages.showMessage("[!] No tienes suficiente oro. Te faltan " + r.getMissingGold() + " monedas.");
        }
    }
}
