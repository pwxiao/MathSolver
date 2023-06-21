package wpxiao.math.solver;

import static android.app.PendingIntent.getActivity;

import static org.scilab.forge.jlatexmath.UnderscoreAtom.s;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;
import wpxiao.math.ExpressionHandler.Expression;
import wpxiao.math.solver.Adapter.ButtonAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setTitle("Solver");
        setContentView(R.layout.activity_main);
//        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        editText = findViewById(R.id.editText);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        textView = findViewById(R.id.text);
        hideSoftInputMethod(editText);
        int nightModeFlags = getBaseContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkTheme = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
        if(isDarkTheme){
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        }
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        editText.addTextChangedListener(this);
        tabLayout.setupWithViewPager(viewPager);

        // 移除TabLayout标签
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        int selectionStart = editText.getSelectionStart();

        if (text.contains("(") && text.contains(")")) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);

            int startIdx = 0;
            int endIdx = 0;
            int cursorPos = selectionStart;

            while (true) {
                startIdx = text.indexOf("(", endIdx);
                if (startIdx == -1) break;

                endIdx = text.indexOf(")", startIdx);
                if (endIdx == -1) break;

                if (cursorPos == endIdx + 1) {
                    builder.setSpan(
                            new ForegroundColorSpan(R.color.cusor),
                            startIdx, endIdx + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cursorPos = endIdx;
                } else {
                    builder.setSpan(
                            new ForegroundColorSpan(Color.BLACK),
                            startIdx, endIdx + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            editText.removeTextChangedListener(this);
            editText.setText(builder);
            editText.setSelection(cursorPos);
            editText.addTextChangedListener(this);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        Expression expression = new Expression(editable.toString());
        String result = String.valueOf(expression.value().val);
        if(result.equals("nan+nan*i")){
            textView.setText("");
        }else{
            textView.setText(result);
        }
    }

    private static class MyPagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_PAGES = 2;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
//                case 2:
//                    return new Fragment3();
                default:
                    throw new IllegalArgumentException("Invalid position: " + position);
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public static class Fragment1 extends Fragment {
        private GridView gridView;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment2, container, false);
            String[] buttonNames = new String[]{
                    "+", "-", "*", "/",
                    "1", "2", "3", "\u232B",
                    "4", "5", "6", "0",
                    "7", "8", "9", ".",
            };
            String[] buttonFormula = new String[]{
                    "+", "-", "*", "/",
                    "1", "2", "3", "<<",
                    "4", "5", "6", "0",
                    "7", "8", "9", "."

            };
            gridView = view.findViewById(R.id.grid_view);
            gridView.setAdapter(new ButtonAdapter(getContext(), buttonNames,buttonFormula));
            return view;

        }
    }

    public static class Fragment2 extends Fragment {
        private GridView gridView;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment2, container, false);
            String[] buttonNames = new String[]{
                    "sin", "cos", "sqrt", "round",
                    ",", "x", "i", "π",
                    "log", "rand", "ln", "tan",
                    "sum", "conj", "fzero", "^",
                    "(",")","arctan","arcsin",
                    "arccos","abs","floor"

            };
            String[] buttonFormula = new String[]{
                    "sin()", "cos()", "sqrt()", "round()",
                    ",", "x",  "i","π",
                    "log()", "rand()", "ln()", "tan()",
                    "sum()", "conj()", "fzero()", "^",
                    "(",")","arctan()","arcsin()",
                    "arccos()","abs()","floor()"

            };
            gridView = view.findViewById(R.id.grid_view);
            gridView.setAdapter(new ButtonAdapter(getContext(), buttonNames,buttonFormula));
            return view;
        }
    }

    public static class Fragment3 extends Fragment {
        @SuppressLint("SetJavaScriptEnabled")
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment3, container, false);

            return view;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.menu_about, Menu.NONE, "关于");
        menu.add(Menu.NONE, R.id.menu_draw, Menu.NONE, "绘图");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_about) {
            startActivity(new Intent(this,AboutActivity.class));
            return true;
        } else if (itemId == R.id.menu_draw) {
            // 处理帮助项的点击事件
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void hideSoftInputMethod(EditText ed) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}