package wpxiao.math.solver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import org.commonmark.node.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

import io.noties.markwon.Markwon;
import io.noties.markwon.core.CorePlugin;
import io.noties.markwon.ext.latex.JLatexMathPlugin;
import io.noties.markwon.inlineparser.MarkwonInlineParserPlugin;

public class AboutActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textView = findViewById(R.id.textView);
        setTitle("About");
        JLatexMathPlugin.create(textView.getTextSize());


        JLatexMathPlugin.create(textView.getTextSize(), new JLatexMathPlugin.BuilderConfigure() {
            @Override
            public void configureBuilder(@NonNull JLatexMathPlugin.Builder builder) {
                // enable inlines (require `MarkwonInlineParserPlugin`), by default `false`
                builder.inlinesEnabled(true);

                // use pre-4.3.0 LaTeX block parsing (by default `false`)
                builder.blocksLegacy(true);

                // by default true
                builder.blocksEnabled(true);

                // @since 4.3.0
                builder.errorHandler(new JLatexMathPlugin.ErrorHandler() {
                    @Nullable
                    @Override
                    public Drawable handleError(@NonNull String latex, @NonNull Throwable error) {
                        // Receive error and optionally return drawable to be displayed instead
                        return null;
                    }
                });

                // executor on which parsing of LaTeX is done (by default `Executors.newCachedThreadPool()`)
                builder.executorService(Executors.newCachedThreadPool());
            }
        });
        Markwon markwon = Markwon.builder(this)
                .usePlugin(CorePlugin.create())
                .usePlugin(MarkwonInlineParserPlugin.create())
                .build();

        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = getAssets().open("1.md");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            markwon.setMarkdown(textView, stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
// set markdown
//        markwon.setMarkdown(textView,getString(R.string.aboiut));


    }
}