# Class Description: MainActivity

## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** Activity
*   **Purpose:** This is the main screen of the application. It acts as the "Controller" that manages the logic for selecting chemical compounds and verifying their elemental composition.
*   **Interaction:** It interacts with the Android system to display the UI, loads data from the project's resources (`strings.xml`), and handles user input via buttons and lists.

---

## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `lvElements` | `ListView` | Displays the list of available chemical elements. | `onCreate` |
| `lvCompounds` | `ListView` | Displays the list of chemical compounds to choose from. | `onCreate` |
| `tvSelectedCompound` | `TextView` | Shows the name of the currently selected compound to the user. | `onItemClick`, `resetSelection` |
| `tvUserSelectedElements` | `TextView` | Displays the elements that the user has selected so far. | `updateUserSelectedElementsTextView` |
| `btnReady` | `Button` | The "Check" button that initiates the verification logic. | `onCreate` |
| `elementsArray` | `String[]` | Stores the raw data of elements loaded from `strings.xml`. | `onCreate`, `onItemClick` |
| `compoundsArray` | `String[]` | Stores the raw data of compounds loaded from `strings.xml`. | `onCreate`, `onItemClick` |
| `currentSelectedCompoundFormula` | `String` | Holds the chemical formula (e.g., "H2O") of the active compound. | Throughout the class to track state. |
| `requiredElementsForCompound` | `List<String>` | A list containing all individual atoms needed for the formula. | `parseFormula`, `btnReady.onClick` |
| `userSelectedElementsList` | `List<String>` | A list of elements the user has tapped on. | `onItemClick`, `btnReady.onClick` |

---

## 3. Class Methods

### Method: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Parameters:**
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `savedInstanceState` | `Bundle` | Contains saved state for activity recreation. |
*   **What it does:**
    1. Sets the visual layout using `setContentView`.
    2. Links Java variables to UI components (buttons, lists) using `findViewById`.
    3. Loads chemical data from the app's resources.
    4. Sets up "Adapters" which tell the lists how to display their items.
    5. Defines "Listeners" (code that runs when a user clicks something).
*   **When called:** Automatically by the Android system when the app starts.
*   **Important:** This is the entry point of the screen. If initialization fails here, the app will crash.

### Method: `updateUserSelectedElementsTextView`
*   **Type:** `private`
*   **Return value:** `void`
*   **Parameters:** None
*   **What it does:** 
    1. Checks if the list of elements selected by the user is empty.
    2. If not empty, it joins the elements into a single string (e.g., "H, H, O") and displays it in the text field.
*   **When called:** Whenever the user adds an element or resets the selection.

### Method: `parseFormula`
*   **Type:** `private`
*   **Return value:** `List<String>` (A list of individual element symbols)
*   **Parameters:**
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `formula` | `String` | The chemical formula to break down (e.g., "H2O"). |
*   **What it does:**
    1. **Normalization:** Converts special subscript characters (like ₂ or ₃) into regular numbers (2 or 3).
    2. **Pattern Matching:** Uses a **Regular Expression** (`[A-Z][a-z]?`) to find element symbols starting with a capital letter.
    3. **Counting:** Looks for numbers following the symbols. If a number exists (e.g., '2'), it adds that element to the list multiple times.
*   **When called:** When the user selects a compound from the list.
*   **Important:** This method is the "brain" of the app. It turns a string like "CO2" into a list: `["C", "O", "O"]`.

---

## 4. Lifecycle

*   **`onCreate()`**:
    *   **When called:** When the activity is first created.
    *   **Action:** Initializes all UI elements and data structures. It's the "setup" phase.

---

## 5. Interface Interaction (UI)
*   **Elements:** 
    *   `ListView`: Used for both Elements and Compounds.
    *   `Button`: Used for the "Ready" check.
    *   `TextView`: Used for status updates.
*   **Connection:** The code uses `findViewById(R.id...)` to link the XML design to Java objects.
*   **Events:** 
    *   `onItemClick`: Detects when a user chooses a compound or an element.
    *   `onClick`: Detects when the "Ready" button is pressed.

---

## 6. Interaction with other components
*   **Resources:** This class heavily relies on `strings.xml` to get the list of chemistry data.
*   **Toasts:** Uses `Toast.makeText()` to show quick popup messages to the user (e.g., "Correct!").

---

## 7. General Logic of the Class
The class follows a simple sequence:
1.  The user picks a **Compound**.
2.  The app calculates what **Elements** are needed (the "Goal").
3.  The user picks elements one by one.
4.  The app compares the user's "Collection" with the "Goal" (after sorting both to ensure the order doesn't matter).
5.  If they match, the user wins!

---

## 8. Simplified Explanation
**Analogy: The Lego Set**
Think of `MainActivity` as a **Lego instruction manual**. 
1.  First, you pick which **model** you want to build (the Compound).
2.  The manual tells you exactly which **bricks** you need (the `parseFormula` logic).
3.  You start picking bricks from a large box (the Elements list).
4.  When you think you're done, you show it to the teacher (the "Ready" button). 
5.  The teacher counts your bricks. If you have exactly what's needed, you get a "Correct!" sticker!

---

## 🛠 Possible Improvements
*   **Parentheses:** The `parseFormula` method cannot handle complex formulas with parentheses like `(NH4)2SO4`. Adding support for nested groups would be a great technical upgrade.
*   **Undo Button:** Currently, if a user misclicks an element, they have to re-select the compound to clear the list. An "Undo" button would improve the User Experience (UX).
