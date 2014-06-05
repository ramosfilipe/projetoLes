package boleiros.povmt.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


public class FragHistorico extends Fragment {

    private final long SEMANA_EM_MS = 604800000;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


    public static FragHistorico newInstance(int sectionNumber){
        FragHistorico fragment = new FragHistorico();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public FragHistorico() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View historicoView = inflater.inflate(R.layout.fragment_historico, container, false);

        ListView listSemanaAtual = (ListView) historicoView.findViewById(R.id.listViewSemanaAtual);
        ListView listSemanaPassada = (ListView) historicoView.findViewById(R.id.listViewSemanaPassada);
        ListView listSemanaRetrasada = (ListView) historicoView.findViewById(R.id.listViewSemanaRetrasada);

        try{
            ArrayList<TempoInvestido> semanaAtual = getRanking(0);
            ArrayList<TempoInvestido> semanaPassada = getRanking(1);
            ArrayList<TempoInvestido> semanaRetrasada = getRanking(2);



            ArrayList<ElementoRankiavel> semanaAtualFinal = geraLista(semanaAtual, getTotalHoras(semanaAtual));
            ArrayList<ElementoRankiavel> semanaPassadaFinal = geraLista(semanaPassada, getTotalHoras(semanaPassada));
            ArrayList<ElementoRankiavel> semanaRetrasadaFinal = geraLista(semanaRetrasada, getTotalHoras(semanaRetrasada));

            Toast t = Toast.makeText(historicoView.getContext(), "Tamanho da lista atual: " + semanaAtualFinal.size(), Toast.LENGTH_SHORT);

            t.show();

            ArrayAdapter<ElementoRankiavel> adaptAtual = new ArrayAdapter<ElementoRankiavel>(
                    historicoView.getContext(),R.layout.simplerow,semanaAtualFinal);

            ArrayAdapter<ElementoRankiavel> adaptPassada = new ArrayAdapter<ElementoRankiavel>(
                    historicoView.getContext(),R.layout.simplerow,semanaPassadaFinal);

            ArrayAdapter<ElementoRankiavel> adaptRetrasada = new ArrayAdapter<ElementoRankiavel>(
                    historicoView.getContext(),R.layout.simplerow,semanaRetrasadaFinal);

            listSemanaAtual.setAdapter(adaptAtual);
            listSemanaPassada.setAdapter(adaptPassada);
            listSemanaRetrasada.setAdapter(adaptRetrasada);

        }catch (Exception e){
            e.printStackTrace();
        }



        return historicoView;
    }


    /**
     *
     * @param semanaDesejada - Semana na qual eu quero obter os Tempos investidos. 0 para semana
     *                       atual, 1 para uma semana atrás e 2 para duas semanas atrás.
     *
     * @return
     * @throws Exception
     */
    private ArrayList<TempoInvestido> getRanking(int semanaDesejada) throws Exception {
        long primeiroDia,ultimoDia;
        DatabaseHelper bd = new DatabaseHelper(this.getActivity());
        List<TempoInvestido> listaTI = bd.getAllTi();


        ArrayList<TempoInvestido> listaResposta = new ArrayList<TempoInvestido>();
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK,1);
        primeiroDia = c.getTimeInMillis() -  (SEMANA_EM_MS * semanaDesejada);
        c.clear();
        c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK,6);
        ultimoDia = c.getTimeInMillis() -  (SEMANA_EM_MS * semanaDesejada);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        for(TempoInvestido ti: listaTI){
            //System.out.println( ti.getCreated_at());
            c.setTime(dateFormat.parse(ti.getCreated_at()));
            long tempoTi = c.getTimeInMillis();
            if(tempoTi>=primeiroDia && tempoTi<=ultimoDia){
                listaResposta.add(ti);
            }
        }


        Collections.sort(listaResposta, new DuracaoTiComparator());

        return listaResposta;


    }

    private ArrayList<ElementoRankiavel> geraLista (ArrayList<TempoInvestido> ranking, int totalDeHoras) throws Exception {
        ArrayList<TempoInvestido> lista = ranking;
        ArrayList<ElementoRankiavel> listaResposta = new ArrayList<ElementoRankiavel>();
        DatabaseHelper db = new DatabaseHelper(this.getActivity());

        for(TempoInvestido elemento: lista){
            Atividade ativ = db.getAtividade(elemento.getIdAtividade());
            ElementoRankiavel  el = new ElementoRankiavel(ativ.getNome(),elemento.getTempoInvestidoMinuto(),(elemento.getTempoInvestidoMinuto()/(float) totalDeHoras));
            listaResposta.add(el);
        }

        return listaResposta;

    }

    private int getTotalHoras(List<TempoInvestido> lista){
        int contador = 0 ;
        for (TempoInvestido el : lista){
            //System.out.println("total de tempo11: "+el.getTempoInvestidoMinuto());

            contador = contador + el.getTempoInvestidoMinuto();
        }
        return  contador;
    }


}
