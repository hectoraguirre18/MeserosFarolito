package com.farolito.meseros.meserosfarolito;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaitlistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaitlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitlistFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView.LayoutManager mLayoutManager;

    private Waitlist_adapter mAdapter;

    TextView TextViewCount;

    private String client_count = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View mView;

    private OnFragmentInteractionListener mListener;

    public WaitlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitlistFragment newInstance(String param1, String param2) {
        WaitlistFragment fragment = new WaitlistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateList(){
        mAdapter.updateData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.updateData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_waitlist, container, false);
        mAdapter = new Waitlist_adapter(getContext());

        mLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.waitlist_recyclerview);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        TextViewCount = (TextView) v.findViewById(R.id.count_tv);

        updateCounter();

        mView = v.findViewById(R.id.waitlist_recyclerview);

        v.findViewById(R.id.waitlist_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                onButtonPressed(view);
            }
        });

        final EditText nameEditText = (EditText) v.findViewById(R.id.client_name_edittext);

        v.findViewById(R.id.addClient_btn_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(!TextUtils.isEmpty(client_count)){
                    if(client_count.length() < 2)
                        client_count = client_count + "0";
                    updateCounter();
                }
            }
        });
        v.findViewById(R.id.addClient_btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "1";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "2";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "3";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "4";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "5";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "6";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "7";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "8";
                updateCounter();
            }
        });
        v.findViewById(R.id.addClient_btn_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(client_count.length() < 2)
                    client_count = client_count + "9";
                updateCounter();
            }
        });

        v.findViewById(R.id.addClient_btn_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if(!TextUtils.isEmpty(client_count)){
                    client_count = removeLastChar(client_count);
                }
                updateCounter();
            }
        });

        ImageButton btn_done = (ImageButton) v.findViewById(R.id.addClient_btn_done);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(nameEditText.getText().toString())){
                    nameEditText.setError("Por favor introduzca un nombre");
                    nameEditText.requestFocus();
                } else if(TextUtils.isEmpty(client_count)){

                    ((EditText) v.findViewById(R.id.count_tv)).setError("Por favor introduzca un número de personas");
                    v.findViewById(R.id.count_tv).requestFocus();
                } else {
                    v.findViewById(R.id.addClient_title).requestFocus();
                    ((EditText) v.findViewById(R.id.count_tv)).setError(null);

                    int count = Integer.parseInt(client_count);
                    String name = nameEditText.getText().toString();

                    nameEditText.setText("");
                    client_count = "";
                    updateCounter();

                    Waitlist_adapter.Client client = new Waitlist_adapter.Client(name, count);
                    int position = mAdapter.getClients().size();
                    mAdapter.insertClient(client, position, mLayoutManager);
                }
            }
        });

        v.findViewById(R.id.count_tv).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                v.findViewById(R.id.count_tv).requestFocus();
                return true;
            }
        });


        return v;
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    private void updateCounter(){
        if (TextUtils.isEmpty(client_count)) {
            TextViewCount.setText("0");
        } else {
            TextViewCount.setText(client_count);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof Waitlist_adapter.waitlistViewHolder){
            List<Waitlist_adapter.Client> clients = mAdapter.getClients();
            String name = clients.get(viewHolder.getAdapterPosition()).name;

            final Waitlist_adapter.Client deletedClient = clients.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            mAdapter.removeClient(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(mView, name + " ha sido eliminado!", Snackbar.LENGTH_LONG);
            snackbar.setAction("DESHACER", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreClient(deletedClient, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View view);
    }
}
