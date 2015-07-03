package com.memo.studygroup.originalmemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	String globalData = "";
	int option = 1;
	int num = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button writebtn = (Button) findViewById(R.id.write);
		Button deletebtn = (Button) findViewById(R.id.delete);
		Button savebtn = (Button) findViewById(R.id.save);
		Button loadbtn = (Button) findViewById(R.id.load);

		writebtn.setOnClickListener(l);
		deletebtn.setOnClickListener(l);
		savebtn.setOnClickListener(l);
		loadbtn.setOnClickListener(l);

		Intent intent = getIntent();
		String memo = (String) intent.getStringExtra("memo");
		option = intent.getIntExtra("option", 1);
		num = intent.getIntExtra("num", 0);
		EditText et = (EditText) findViewById(R.id.edit);
		globalData = memo;
		et.setText(globalData);

		Toast.makeText(this, "" + memo + option, Toast.LENGTH_SHORT).show();

		//createTable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	View.OnClickListener l = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.write) {
				Intent intent = new Intent(MainActivity.this, MemoListActivity.class);

				startActivity(intent);

			} else if (v.getId() == R.id.delete) {
				EditText et = (EditText) findViewById(R.id.edit);
				globalData = "";
				et.setText(globalData);
				String str = globalData;
				Intent data = getIntent();
				data.putExtra("save", str);
				data.putExtra("num", num);

				setResult(3, data);

				finish();

			} else if (v.getId() == R.id.save) {
				EditText et = (EditText) findViewById(R.id.edit);
				String str = et.getText().toString();

				globalData = str;
				Toast.makeText(v.getContext(), "" + str, Toast.LENGTH_SHORT).show();

				Intent data = getIntent();
				data.putExtra("save", str);
				data.putExtra("num", num);

				if (option == 1) {
					setResult(1, data);
				} else {
					setResult(2, data);
				}

				finish();

			} else if (v.getId() == R.id.load) {
				EditText et = (EditText) findViewById(R.id.edit);
				et.setText(globalData);

			}
		}
	};

}
