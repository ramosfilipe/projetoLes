package boleiros.povmt.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
    private static final String ARG_SECTION_NUMBER = "section_number";

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
        // Required empty public constructor
    }



    private ArrayList<TempoInvestido> getRanking() throws Exception {


        long primeiroDia,ultimoDia;
        DatabaseHelper bd = new DatabaseHelper(this.getActivity());
        List<TempoInvestido> listaTI = bd.getAllTi();
        System.out.println(listaTI.get(0).getCreated_at());
        System.out.println("aqi");
        ArrayList<TempoInvestido> listaResposta = new ArrayList<TempoInvestido>();
        Calendar c = Calendar.getInstance();

        c.getFirstDayOfWeek();
        primeiroDia = c.getTimeInMillis();
        c.clear();
        c.set(Calendar.DAY_OF_WEEK,6);
        ultimoDia = c.getTimeInMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        for(TempoInvestido ti: listaTI){
            System.out.println( ti.getCreated_at());
            c.setTime(dateFormat.parse(ti.getCreated_at()));
            long tempoTi = c.getTimeInMillis();
            if(tempoTi>=primeiroDia && tempoTi<=ultimoDia){
                listaResposta.add(ti);
            }
        }
        Collections.sort(listaResposta,new DuracaoTiComparator());

        return listaResposta;
    }

    private ArrayList<ElementoRankiavel> geraLista (ArrayList<TempoInvestido> ranking, int totalDeHoras) throws Exception {
        ArrayList<TempoInvestido> lista = ranking;
        ArrayList<ElementoRankiavel> listaResposta = new ArrayList<ElementoRankiavel>();
        DatabaseHelper db = new DatabaseHelper(this.getActivity());

        for(TempoInvestido elemento: lista){
            Atividade ativ = db.getAtividade(elemento.getIdAtividade());
            ElementoRankiavel  el = new ElementoRankiavel(ativ.getNome(),elemento.getTempoInvestidoMinuto(),elemento.getTempoInvestidoMinuto()/totalDeHoras);
            listaResposta.add(el);
        }

        return listaResposta;

    }

    private int getTotalHoras(List<TempoInvestido> lista){
        int contador = 0 ;
        for (TempoInvestido el : lista){
            contador = contador + el.getTempoInvestidoMinuto();
        }
        return  contador;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_frag_acompanhamento, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.listViewRanking);


        try {
            ArrayList<TempoInvestido> array = getRanking();
            int totalTempo = getTotalHoras(array);
            TextView tx = (TextView) rootView.findViewById(R.id.textViewResultadoDeHoras);
            ArrayList<ElementoRankiavel> arrayFinal = geraLista(array,totalTempo);
            ArrayAdapter<ElementoRankiavel> adapt = new ArrayAdapter<ElementoRankiavel>(rootView.getContext(),R.layout.simplerow,arrayFinal);
            tx.setText(totalTempo);
            System.out.println(totalTempo);

            list.setAdapter(adapt);

        } catch (Exception e) {
            e.printStackTrace();
        }




        return rootView;

    }



}
