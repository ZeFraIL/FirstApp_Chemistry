# 📱 Android Application Documentation: FirstApp_Chemistry

## 🧾 General Information
**Project Name:** FirstApp_Chemistry  
**Author(s):** Zeev Fraiman  
**Date:** July 4, 2026  
**Language:** Java  
**IDE:** Android Studio  
**Android Version (minSdk / targetSdk):** 28 / 36  

---

## 🎯 Project Goal
*   **Problem Solver:** This application helps students and chemistry enthusiasts learn and memorize the chemical composition of various compounds.
*   **Importance:** Learning chemistry can be challenging; an interactive way to "assemble" compounds from elements makes the process more engaging and memorable.
*   **Target Audience:** Students, pupils, and anyone interested in basic chemistry.

---

## 📌 Application Requirements
### Functional Requirements
*   Display a list of common chemical compounds.
*   Display a list of chemical elements.
*   Allow the user to select a compound and then "build" it by selecting elements.
*   Validate if the user's selection matches the chemical formula.
*   Provide feedback via Toast messages (Correct/Incorrect).

### Non-functional Requirements
*   **Performance:** Instant response to user clicks and formula parsing.
*   **Usability:** Simple one-screen interface with clear lists.
*   **Reliability:** Robust formula parsing using Regular Expressions.

---

## 🧠 General Architecture
*   **Approach:** MVC (Model-View-Controller) / Simple Activity-based.
*   **Reasoning:** Given the simple nature of the project (single screen, local data), a standard Activity-centric approach is efficient and easy to maintain without unnecessary boilerplate.
*   **Core Components:**
    *   `MainActivity`: Handles UI logic and user interaction.
    *   `strings.xml`: Acts as the data source (arrays of elements and compounds).
    *   `parseFormula()`: Internal logic for decomposition of chemical formulas.

---

## 🧩 UML Diagram
`[MainActivity] -> [Resources (strings.xml)]`  
`[MainActivity] -> [Regex Parser]`

---

## 🧩 Detailed Class Description
### 📌 Class: MainActivity
*   **Role:** The primary controller and view of the application.
*   **Responsibility:** Initializing UI, loading data from resources, handling clicks on lists, and validating formulas.
*   **Main Methods:**
    *   `onCreate()`: Sets up the layout, adapters, and listeners.
    *   `parseFormula(String formula)`: Uses Regex to extract elements and their quantities from a string (e.g., "H₂O").
    *   `updateUserSelectedElementsTextView()`: Updates the UI to show the current selection.
*   **Interaction:** Communicates with `ListView` adapters and uses standard Android Toast for user notifications.

---

## 🔄 App Workflow
1.  User selects a compound (e.g., Water - H₂O).
2.  App parses the formula and clears previous element selections.
3.  User taps on elements (e.g., H, H, O) in the elements list.
4.  User clicks "Ready".
5.  App compares the sorted list of required elements with the user's selection and shows the result.

---

## 🎨 UI/UX Analysis
*   **Design Choice:** Split-screen layout with two lists for easy comparison and selection.
*   **Principles:**
    *   **Simplicity:** No hidden menus; everything is on one screen.
    *   **Logic:** Top-to-bottom flow (Select compound -> Select elements -> Check).
    *   **Accessibility:** Large text in list items for readability.
*   **Improvements:** Add images of compounds or a periodic table visualization.

---

## ⚙️ Threading
*   **Mechanism:** UI Thread (Main Thread).
*   **Reasoning:** All operations (parsing small strings) are extremely fast and do not block the UI.
*   **Prevention:** Logic is kept lightweight to avoid ANR.

---

## 💾 Data Management
*   **Storage:** Android Resource files (`strings.xml`).
*   **Reasoning:** Static data that doesn't change often is best kept in resources for localization support and simplicity.
*   **Safety:** Data is read-only at runtime, ensuring consistency.

---

## 🧪 Testing
*   **Manual Testing:** Verifying all compounds in the list.
*   **Validation:** Testing the Regex parser with various formulas (subscripts, single/double letter symbols).

---

## 🐞 Error Handling
*   **Scenarios:**
    *   Clicking "Ready" without selecting a compound.
    *   Clicking elements without selecting a compound.
*   **Response:** Informative Toast messages guiding the user.

---

## ⚡ Performance
*   **Optimization:** Use of `ArrayAdapter` with custom layouts for efficient list rendering.
*   **Bottlenecks:** None expected for the current data size.

---

## 🚀 Expansion Possibilities
*   Add more complex compounds.
*   Add a "Quiz Mode" with a timer.
*   Support for multiple languages (localization).
*   Integration with a Web API for a larger chemical database.

---

## 📊 Project Self-Assessment
| Criterion | Rating (1–10) |
| :--- | :--- |
| Architecture | 8 |
| Code | 9 |
| UI/UX | 8 |
| Reliability | 10 |
| **Overall Level** | **8.7** |
