package com.farolito.meseros.meserosfarolito;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
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
 */
public class WaitlistFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    RecyclerView.LayoutManager mLayoutManager;

    private Waitlist_adapter mAdapter;

    TextView TextViewCount;

    private String client_count = "";

    private static final int MAX_DIGITS = 2;

    private RecyclerView mRecyclerView;

    private OnFragmentInteractionListener mListener;

    private EditText mFocusThief;

    public WaitlistFragment() {
        // Required empty public constructor
    }

    public void updateList(){
        mAdapter.updateData();
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
        mFocusThief = v.findViewById(R.id.focus_thief);

        mAdapter = new Waitlist_adapter(getContext());

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView = (RecyclerView) v.findViewById(R.id.waitlist_recyclerview);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        TextViewCount = (TextView) v.findViewById(R.id.count_tv);

        TextViewCount.setInputType(InputType.TYPE_NULL);

        updateCounter();

        v.findViewById(R.id.waitlist_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                onButtonPressed(view);
            }
        });

        final EditText nameEditText = (EditText) v.findViewById(R.id.client_name_edittext);

        v.findViewById(R.id.addClient_btn_0).setOnClickListener(numericButtonListener(v, 0));
        v.findViewById(R.id.addClient_btn_1).setOnClickListener(numericButtonListener(v, 1));
        v.findViewById(R.id.addClient_btn_2).setOnClickListener(numericButtonListener(v, 2));
        v.findViewById(R.id.addClient_btn_3).setOnClickListener(numericButtonListener(v, 3));
        v.findViewById(R.id.addClient_btn_4).setOnClickListener(numericButtonListener(v, 4));
        v.findViewById(R.id.addClient_btn_5).setOnClickListener(numericButtonListener(v, 5));
        v.findViewById(R.id.addClient_btn_6).setOnClickListener(numericButtonListener(v, 6));
        v.findViewById(R.id.addClient_btn_7).setOnClickListener(numericButtonListener(v, 7));
        v.findViewById(R.id.addClient_btn_8).setOnClickListener(numericButtonListener(v, 8));
        v.findViewById(R.id.addClient_btn_9).setOnClickListener(numericButtonListener(v, 9));

        v.findViewById(R.id.addClient_btn_backspace).setOnClickListener(numericButtonListener(v, -1));

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
                hideKeyboard(getActivity());
                v.findViewById(R.id.count_tv).requestFocus();
                return true;
            }
        });


        return v;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private View.OnClickListener numericButtonListener(final View v, final int digit){

        if(digit == -1){
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    v.findViewById(R.id.count_tv).requestFocus();
                    if(!TextUtils.isEmpty(client_count)){
                        client_count = removeLastChar(client_count);
                    }
                    updateCounter();
                }
            };
        }

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.count_tv).requestFocus();
                if (!TextUtils.isEmpty(client_count) || digit != 0) {
                    if (client_count.length() < 2)
                        client_count = client_count + String.valueOf(digit);
                    updateCounter();
                }
            }
        };
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
                    .make(mRecyclerView, name + " ha sido eliminado!", Snackbar.LENGTH_LONG);
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
