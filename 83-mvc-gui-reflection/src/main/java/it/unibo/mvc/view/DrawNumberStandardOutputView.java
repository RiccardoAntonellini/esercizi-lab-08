package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawResult;

/**
 * implementazione della standardoutputview solo output su terminale.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    /**
     * crea una nuova view che non prende input.
     */
    public DrawNumberStandardOutputView() {
        /*
     * crea una nuova view che non prende input
     */
    }

    @Override
    public void start() {
        /*
         * This UI is output only.
         */
    }

    @Override
    public void setController(final DrawNumberController observer) {
        /*
         * This UI is output only.
         */
    }

    @Override
    @SuppressWarnings("PMD.SystemPrintln")
    public void result(final DrawResult res) {
        System.out.println(res.getDescription());
    }
}
