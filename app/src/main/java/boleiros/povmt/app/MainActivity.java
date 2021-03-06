package boleiros.povmt.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import boleiros.povmt.app.model.Atividade;
import boleiros.povmt.app.model.TempoInvestido;
import util.DatabaseHelper;
import util.MyNumberPicker;


public class MainActivity extends Activity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    static String emailText, nameText;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    AccountManager mAccountManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Intent intent = getIntent();
        emailText = intent.getStringExtra("email_id");
        if (emailText != null) {
            Log.d("Email id: ", emailText);

           System.out.println("On Home Page***" + AbstractGetNameTask.GOOGLE_USER_DATA);

            try {
                JSONObject profileData = new JSONObject(AbstractGetNameTask.GOOGLE_USER_DATA);

                if (profileData.has("picture")) {
                    Log.i("Tem foto? ", "sim. URL: " + profileData.getString("picture"));
                }
                if (profileData.has("name")) {
                    nameText = profileData.getString("name");
                    Log.i("Tem nome? ", "sim, " + nameText);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });




        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        String accountName = getAccountNames()[0];
        Log.i("Conta: ", accountName);



    }


    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(
                GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case(0):
                    return  boleiros.povmt.app.FragHistorico.newInstance(position);

                case(1):
                    return  PlaceholderFragment.newInstance(position);

                case(2):
                    return  FragAcompanhamento.newInstance(position);

                default:
                    return  PlaceholderFragment.newInstance(position);


            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                default:
                    System.out.println("Opcao Invalida.");

            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private  String emailText;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {


            final View rootView;
            rootView = inflater.inflate(R.layout.fragment_main, container, false);

            if (nameText != null) {
                final TextView cabecalho = (TextView) rootView.findViewById(R.id.textView2);
                cabecalho.setText("Olá, " + nameText);

            }


            final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                    R.array.lista_de_prioridades, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            Object position = spinner.getSelectedItem();


            final Button confirma = (Button) rootView.findViewById(R.id.botaoConfirmarTi);
            confirma.setOnClickListener(new View.OnClickListener() {

                AutoCompleteTextView texto = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
                MyNumberPicker horas = (MyNumberPicker) rootView.findViewById(R.id.numberPicker1);
                MyNumberPicker minutos = (MyNumberPicker) rootView.findViewById(R.id.numberPicker2);
                Object position = spinner.getSelectedItem();


                DatabaseHelper bd = new DatabaseHelper(rootView.getContext());
                public void onClick(View v) {
                    position = spinner.getSelectedItem();
                    TempoInvestido ti = new TempoInvestido();
                    try {
                        Atividade ativ;
                        long idAtividade;
                        String nomeAtividade = texto.getText().toString();
                        String prioridadeAtividade = position.toString();

                        if (bd.isActivityOnDB(nomeAtividade)) {
                            ativ = bd.getAtividadeByName(nomeAtividade);
                            idAtividade = ativ.getId();
                        } else {
                            ativ = new Atividade();
                            ativ.setPrioridade(prioridadeAtividade);
                            ativ.setNome(nomeAtividade);
                            idAtividade = bd.createAtividade(ativ);
                        }
                        ti.setTempoInvestidoMinuto((horas.getValue() * 60) + minutos.getValue());
                        bd.createTI(ti, idAtividade);
                        Log.d("Tag", "msg " + bd.getAtividadeCount());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        List<Atividade> lista = bd.getAllAtividades();
                        Collections.reverse(lista);
                        ListView list = (ListView) rootView.findViewById(R.id.listView);
                        ArrayAdapter<Atividade> adapt = new ArrayAdapter<Atividade>(rootView.getContext(), R.layout.simplerow, lista);
                        list.setAdapter(adapt);
                        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
                        autoCompleteTextView.setAdapter(adapt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    texto.setText("");

                }
            });


            final Button login = (Button) rootView.findViewById(R.id.button_login);
            login.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(intent);



                }
            });



            DatabaseHelper bd = new DatabaseHelper(this.getActivity());
            position = spinner.getSelectedItem();
            ListView list = (ListView) rootView.findViewById(R.id.listView);
            try {
                List<Atividade> lista = bd.getAllAtividades();
                Collections.reverse(lista);
                ArrayAdapter<Atividade> adapt = new ArrayAdapter<Atividade>(rootView.getContext(), R.layout.simplerow, lista);
                list.setAdapter(adapt);
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
                autoCompleteTextView.setAdapter(adapt);
            } catch (Exception e) {
                e.printStackTrace();
            }


            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
