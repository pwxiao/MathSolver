package wpxiao.math.solver.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import wpxiao.math.ExpressionHandler.Expression;
import wpxiao.math.solver.R;

public class ButtonAdapter extends BaseAdapter {

    private final Activity activity;
    private final String[] mButtonNames;
    private String[] mbuttonFormula;

    public ButtonAdapter(Context context, String[] buttonNames, String[] buttonFormula) {
        activity = (Activity) context;
        mButtonNames = buttonNames;
        mbuttonFormula = buttonFormula;

    }

    @Override
    public int getCount() {
        return mButtonNames.length;
    }

    @Override
    public Object getItem(int position) {
        return mButtonNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            button = (Button) inflater.inflate(R.layout.button_item, parent, false);
        } else {
            button = (Button) convertView;
        }
        button.setTag(position);
        button.setText(mButtonNames[position]);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = activity.findViewById(R.id.editText);
                Editable editable = editText.getText();
                Button button1 = (Button) view;
                int startIndex = editText.getSelectionStart();
                if(button1.getText().equals("\u232B")){
                    if(startIndex>0){
                        editable.delete(startIndex-1, startIndex);
                    }
                }else {
                    editable.insert(startIndex, mbuttonFormula[position]);
                }

            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Button button1 = (Button) view;
                EditText editText = activity.findViewById(R.id.editText);
                if(button1.getText().equals("\u232B")){
                    editText.setText("");
                }
                return false;
            }
        });
        return button;
    }


}