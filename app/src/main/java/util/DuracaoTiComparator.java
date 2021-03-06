package util;

import java.io.Serializable;
import java.util.Comparator;

import boleiros.povmt.app.model.TempoInvestido;

/**
 * Criado por Filipe Ramos em 03/06/14 as 23.
 */
public class DuracaoTiComparator implements Comparator<TempoInvestido>, Serializable {

    @Override
    public int compare(TempoInvestido tempoInvestido, TempoInvestido tempoInvestido2) {
        return tempoInvestido2.getTempoInvestidoMinuto() - tempoInvestido.getTempoInvestidoMinuto();
    }
}
