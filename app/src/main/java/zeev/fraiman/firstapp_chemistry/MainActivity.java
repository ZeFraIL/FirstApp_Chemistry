package zeev.fraiman.firstapp_chemistry;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ListView lvElements;
    private ListView lvCompounds;
    private TextView tvSelectedCompound;
    private TextView tvUserSelectedElements;
    private Button btnReady;

    private String[] elementsArray;
    private String[] compoundsArray;

    private String currentSelectedCompoundName = "";
    private String currentSelectedCompoundFormula = "";
    private List<String> requiredElementsForCompound = new ArrayList<>();
    private List<String> userSelectedElementsList = new ArrayList<>();

    private View previouslySelectedCompoundView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        lvElements = findViewById(R.id.lvElements);
        lvCompounds = findViewById(R.id.lvCompounds);
        tvSelectedCompound = findViewById(R.id.tvSelectedCompound);
        tvUserSelectedElements = findViewById(R.id.tvUserSelectedElements);
        btnReady = findViewById(R.id.btnReady);

        // Load data from resources
        elementsArray = getResources().getStringArray(R.array.elements_array);
        compoundsArray = getResources().getStringArray(R.array.compounds_array);

        // Setup Adapters
        ArrayAdapter<String> elementsAdapter = new ArrayAdapter<>(
                this, R.layout.list_item_element, R.id.tv_element_item_text, elementsArray);
        lvElements.setAdapter(elementsAdapter);

        ArrayAdapter<String> compoundsAdapter = new ArrayAdapter<>(
                this, R.layout.list_item_compound, R.id.tv_compound_item_text, compoundsArray);
        lvCompounds.setAdapter(compoundsAdapter);

        // Set initial text for TextViews
        tvSelectedCompound.setText(getString(R.string.selected_compound_placeholder));
        tvUserSelectedElements.setText(getString(R.string.user_elements_placeholder));

        // Setup Listeners
        lvCompounds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Reset background of previously selected item
                if (previouslySelectedCompoundView != null) {
                    previouslySelectedCompoundView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.list_item_compound_bg));
                }

                // Highlight newly selected item
                view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.list_item_compound_selected_bg));
                previouslySelectedCompoundView = view;

                currentSelectedCompoundName = compoundsArray[position];
                currentSelectedCompoundFormula = currentSelectedCompoundName.split(" – ")[0];
                tvSelectedCompound.setText(getString(R.string.selected_compound_placeholder) + " " + currentSelectedCompoundFormula);

                requiredElementsForCompound = parseFormula(currentSelectedCompoundFormula);
                userSelectedElementsList.clear();
                updateUserSelectedElementsTextView();
            }
        });

        lvElements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentSelectedCompoundFormula.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_select_compound_first), Toast.LENGTH_SHORT).show();
                    return;
                }
                String selectedElementSymbol = elementsArray[position].split(" – ")[0];
                userSelectedElementsList.add(selectedElementSymbol);
                updateUserSelectedElementsTextView();
            }
        });

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelectedCompoundFormula.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_select_compound_first), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userSelectedElementsList.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_select_elements_first), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> sortedRequired = new ArrayList<>(requiredElementsForCompound);
                Collections.sort(sortedRequired);
                List<String> sortedUserSelected = new ArrayList<>(userSelectedElementsList);
                Collections.sort(sortedUserSelected);

                if (sortedRequired.equals(sortedUserSelected)) {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_correct_selection), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_incorrect_try_again), Toast.LENGTH_LONG).show();
                }
                // Optionally, reset after check
                // resetSelection(); 
            }
        });
    }

    private void updateUserSelectedElementsTextView() {
        if (userSelectedElementsList.isEmpty()) {
            tvUserSelectedElements.setText(getString(R.string.user_elements_placeholder));
        } else {
            tvUserSelectedElements.setText(getString(R.string.user_elements_placeholder) + " " + String.join(", ", userSelectedElementsList));
        }
    }

    // Optional: Method to reset selection state if needed later
    private void resetSelection() {
        if (previouslySelectedCompoundView != null) {
            previouslySelectedCompoundView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.list_item_compound_bg));
            previouslySelectedCompoundView = null;
        }
        currentSelectedCompoundFormula = "";
        currentSelectedCompoundName = "";
        tvSelectedCompound.setText(getString(R.string.selected_compound_placeholder));
        userSelectedElementsList.clear();
        requiredElementsForCompound.clear();
        updateUserSelectedElementsTextView();
        // Consider also resetting lvCompounds selection state if the adapter supports it easily
        // or if a "none selected" visual state is desired for lvCompounds itself.
    }

    private List<String> parseFormula(String formula) {
        List<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("([A-Z][a-z]?)(\\d*)");
        Matcher matcher = pattern.matcher(formula);

        String normalizedFormula = formula
                .replace('₂', '2')
                .replace('₃', '3')
                .replace('₄', '4')
                .replace('₅', '5')
                .replace('₆', '6')
                .replace('₇', '7')
                .replace('₈', '8')
                .replace('₉', '9');

        matcher = pattern.matcher(normalizedFormula);

        while (matcher.find()) {
            String element = matcher.group(1);
            String countStr = matcher.group(2);
            int count = countStr.isEmpty() ? 1 : Integer.parseInt(countStr);
            for (int i = 0; i < count; i++) {
                elements.add(element);
            }
        }
        return elements;
    }
}
