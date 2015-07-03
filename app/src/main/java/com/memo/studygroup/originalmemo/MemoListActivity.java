package com.memo.studygroup.originalmemo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MemoListActivity extends ListActivity implements OnItemClickListener {
	ListView listview;

	ArrayList<String> sample = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	private SQLiteDatabase mDatabase;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, sample);

		setListAdapter(adapter);

		listview = this.getListView();
		listview.setOnItemClickListener(this);

		createTable();

		setting();

		Button writebtn = (Button) findViewById(R.id.listwrite);

		writebtn.setOnClickListener(l);

	}

	private void createTable() {
		// TODO Auto-generated method stub
		mDatabase = openOrCreateDatabase("memo.db",
			SQLiteDatabase.CREATE_IF_NECESSARY, null);

		String search = "select name from sqlite_master where type='table' and name='memotable';";
		Cursor c = mDatabase.rawQuery(search, null);

		Log.d("log0Count::", "" + c.getCount());

		if (c.getCount() == 0) {
			String sql = "create table memotable " +
				"(seq integer primary key , " +
				"memo text not null, " +
				"regdate text not null);";
			mDatabase.execSQL(sql);

			long result = 0;

			ContentValues values = new ContentValues();
			values.put("memo", "test4");
			values.put("regdate", "20150619");
			result = mDatabase.insert("memotable", null, values);
			values.put("memo", "test3");
			values.put("regdate", "20150619");
			result = mDatabase.insert("memotable", null, values);

			values.put("memo", "test2");
			values.put("regdate", "20150619");
			result = mDatabase.insert("memotable", null, values);
			values.put("memo", "test1");
			result = mDatabase.insert("memotable", null, values);


		}
		mDatabase.close();
	}

	void setting() {
		mDatabase = openOrCreateDatabase("memo.db",
			SQLiteDatabase.CREATE_IF_NECESSARY, null);

		String search = "select seq, memo, regdate from memotable;";
		Cursor c = mDatabase.rawQuery(search, null);

		if (c.getCount() != 0) {
			while (c.moveToNext()) {
				sample.add((String) c.getString(1));
			}
		}

		adapter.notifyDataSetChanged();
		mDatabase.close();
	}

	void insert(String data) {
		mDatabase = openOrCreateDatabase("memo.db",
			SQLiteDatabase.CREATE_IF_NECESSARY, null);

		ContentValues values = new ContentValues();

		values.put("memo", data);
		values.put("regdate", "20150619");

		long result = mDatabase.insert("memotable", null, values);

		mDatabase.close();
	}

	void update(int num, String data) {
		mDatabase = openOrCreateDatabase("memo.db",
			SQLiteDatabase.CREATE_IF_NECESSARY, null);

		ContentValues values = new ContentValues();

		values.put("memo", data);
		String[] nums = { "" + num };

		long result = mDatabase.update("memotable", values, "seq=?", nums);

		mDatabase.close();
	}

	void delete(int num) {
		mDatabase = openOrCreateDatabase("memo.db",
			SQLiteDatabase.CREATE_IF_NECESSARY, null);

		int result = mDatabase.delete("memotable", "seq=?",
			new String[] { "" + num });

		mDatabase.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	View.OnClickListener l = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.listwrite) {
				Intent intent = new Intent(MemoListActivity.this, MainActivity.class);

				intent.putExtra("memo", "");
				startActivityForResult(intent, 1);

			}
		}

	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {

			String listdata = (String) data.getStringExtra("save");

			Toast.makeText(this, "test" + listdata, Toast.LENGTH_SHORT).show();

			sample.add(listdata);

			adapter.notifyDataSetChanged();
			insert(listdata);
		} else if (resultCode == 2) {
			String listdata = (String) data.getStringExtra("save");
			int position = data.getIntExtra("num", 0);

			sample.set(position, listdata);
			adapter.notifyDataSetChanged();

			update(position + 1, listdata);
		} else if (resultCode == 3) {
			String listdata = (String) data.getStringExtra("save");
			int position = data.getIntExtra("num", 0);
			sample.remove(position);
			adapter.notifyDataSetChanged();
			delete(position + 1);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(MemoListActivity.this, MainActivity.class);

		intent.putExtra("memo", "" + arg0.getItemAtPosition(position));
		intent.putExtra("option", 2); //option == 2 ���� 
		intent.putExtra("num", position);
		startActivityForResult(intent, 1);

	}

}
