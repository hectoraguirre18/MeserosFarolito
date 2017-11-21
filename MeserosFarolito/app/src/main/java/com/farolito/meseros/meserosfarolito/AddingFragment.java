package com.farolito.meseros.meserosfarolito;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddingFragment newInstance(String param1, String param2) {
        AddingFragment fragment = new AddingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_adding, container, false);

        final EditText nameEditText = (EditText) v.findViewById(R.id.edittext_name);
        final EditText countEditText = (EditText) v.findViewById(R.id.edittext_count);
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(nameEditText, InputMethodManager.SHOW_IMPLICIT);
        nameEditText.requestFocus();

        Button b = (Button) v.findViewById(R.id.button_add_client);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(nameEditText.getText().toString())){
                    nameEditText.setError("Por favor introduzca un nombre");
                    nameEditText.requestFocus();
                } else if(TextUtils.isEmpty(countEditText.getText().toString())){
                    countEditText.setError("Por favor introduzca un numero de personas");
                    countEditText.requestFocus();
                } else {
                    if(countEditText.isFocused()) {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(countEditText.getWindowToken(), 0);
                    } else if(nameEditText.isFocused()){
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
                    }

                    int count = Integer.parseInt(countEditText.getText().toString());
                    String name = nameEditText.getText().toString();

                    WaitlistDBHelper mDbHelper = new WaitlistDBHelper(getContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_NAME, name);
                    values.put(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_COUNT, count);

                    db.insert(WaitlistContract.WaitlistEntry.WAITLIST_TABLE_NAME, null, values);

                    if (mListener != null) {
                        mListener.onFragmentInteraction(view);
                    }
                }
            }
        });

        return v;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(View view);
    }
}
