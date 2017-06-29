/*
 * Created by Roshan Mahes on 8-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.CalendarView;

import static mprog.nl.a10973710.dartsworld.Helper.displayAlertDialog;
import static mprog.nl.a10973710.dartsworld.Helper.isConnectedToInternet;
import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;

public class CalendarActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    String date;
    String formatedDate;
    int singleDigits = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        if (isConnectedToInternet(CalendarActivity.this)) {
            setUpBars(CalendarActivity.this, "Calendar");
            CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
            dateListener(calendarView);

        } else {
            displayAlertDialog(CalendarActivity.this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo("CalendarActivity", CalendarActivity.this, id, drawer);
        return true;
    }

    /**
     * Listens for date clicks and converts the selected date to API-friendly versions.
     * Loads data if date is selected.
     */
    private void dateListener(CalendarView calView) {

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String realMonth;
                String realDay;

                // months apparently are zero-indexed
                if (month < singleDigits - 1) {
                    realMonth = "0" + String.valueOf(month + 1);
                } else {
                    realMonth = String.valueOf(month + 1);
                }

                if (dayOfMonth < singleDigits) {
                    realDay = "0" + String.valueOf(dayOfMonth);
                } else {
                    realDay = String.valueOf(dayOfMonth);
                }

                date = year + "-" + realMonth + "-" + realDay;
                formatedDate = realDay + "." + realMonth + "." + year + ".";

                loadData(date);
            }
        });
    }

    public void loadData(String date) {
        DateAsyncTask asyncTask = new DateAsyncTask(this, date);
        asyncTask.execute(date);
    }

    /**
     * Starts the DateActivity giving the data and the selected date.
     */
    public void startDateActivity(String data, String date) {

        Intent intent = new Intent(this, DateActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("date", date);
        intent.putExtra("formatedDate", formatedDate);
        this.startActivity(intent);
    }
}