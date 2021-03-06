package boleiros.povmt.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import boleiros.povmt.app.model.Atividade;
import boleiros.povmt.app.model.ElementoRankiavel;
import boleiros.povmt.app.model.TempoInvestido;
import util.DatabaseHelper;
import util.DuracaoTiComparator;


public class FragAcompanhamento extends Fragment  {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "2";
    private static final int NUMERO_DE_MINUTOS_EM_UMA_HORA = 60;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


    // TODO: Rename and change types and number of parameters
    public static FragAcompanhamento newInstance(int sectionNumber) {
        FragAcompanhamento fragment = new FragAcompanhamento();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public FragAcompanhamento() {
    }

    private ArrayList<TempoInvestido> getRanking() throws Exception {
        long primeiroDia, ultimoDia;
        DatabaseHelper bd = new DatabaseHelper(this.getActivity());
        List<TempoInvestido> listaTI = bd.getAllTi();
        ArrayList<TempoInvestido> listaResposta = new ArrayList<TempoInvestido>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 1);
        primeiroDia = c.getTimeInMillis();
        c.clear();
        c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 7);
        ultimoDia = c.getTimeInMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        for (TempoInvestido ti: listaTI) {
            //System.out.println( ti.getCreated_at());
            c.setTime(dateFormat.parse(ti.getCreated_at()));
            long tempoTi = c.getTimeInMillis();
            if (tempoTi >= primeiroDia && tempoTi <= ultimoDia) {
                listaResposta.add(ti);
            }
        }


        Collections.sort(listaResposta, new DuracaoTiComparator());

        return listaResposta;
    }

    private ArrayList<ElementoRankiavel> geraLista(ArrayList<TempoInvestido> ranking,
                                                    int totalDeHoras) throws Exception {
        ArrayList<TempoInvestido> lista = ranking;
        ArrayList<ElementoRankiavel> listaResposta = new ArrayList<ElementoRankiavel>();
        DatabaseHelper db = new DatabaseHelper(this.getActivity());

        for (TempoInvestido elemento: lista) {
            Atividade ativ = db.getAtividade(elemento.getIdAtividade());
            ElementoRankiavel  el = new ElementoRankiavel(ativ.getNome(),
                    elemento.getTempoInvestidoMinuto(),
                    (elemento.getTempoInvestidoMinuto() / (float) totalDeHoras), ativ.getPrioridade());
            if (listaResposta.contains(el)) {
                listaResposta.get(listaResposta.indexOf(el)).somaMinutos(el.getHoras());
            } else {
                listaResposta.add(el);
            }
        }
        return listaResposta;
    }
    private int getTotalHoras(List<TempoInvestido> lista) {
        int contador = 0;
        for (TempoInvestido el : lista) {
            contador = contador + el.getTempoInvestidoMinuto();
        }
        return  contador;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.fragment_frag_acompanhamento, container, false);
        ListView list = (ListView) rootView2.findViewById(R.id.listViewRanking);
        TextView tx = (TextView) rootView2.findViewById(R.id.textViewTotaldeHoras);
        try {
            ArrayList<TempoInvestido> array = getRanking();
            // System.out.println(array.get(0));
            int totalTempo = getTotalHoras(array);
            if (totalTempo % NUMERO_DE_MINUTOS_EM_UMA_HORA == 0) {
                tx.setText("Tempo total investido: " + totalTempo / NUMERO_DE_MINUTOS_EM_UMA_HORA
                        + " horas");
            } else {
                tx.setText("Tempo total investido: " + totalTempo / NUMERO_DE_MINUTOS_EM_UMA_HORA
                        + " horas e " + totalTempo % NUMERO_DE_MINUTOS_EM_UMA_HORA + " minutos");
            }
            ArrayList<ElementoRankiavel> arrayFinal = geraLista(array, totalTempo);
            ArrayAdapter<ElementoRankiavel> adapt = new ArrayAdapter<ElementoRankiavel>(rootView2.getContext(),
                                                                   R.layout.simplerow, arrayFinal);
            // tx.setText(totalTempo);

            list.setAdapter(adapt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView2;
    }
}
