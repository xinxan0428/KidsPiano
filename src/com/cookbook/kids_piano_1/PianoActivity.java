package com.cookbook.kids_piano_1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;

public class PianoActivity extends Activity {
	private Resources res;

	private String[][] hintStrings = {
			{ "do", "re", "mi", "fa", "sol", "la", "ti", "DO" },
			{ "c", "d", "e", "f", "g", "a", "b", "C" } };
	private String[][] lyrics = new String[5][];
	private int[][] hints = new int[5][];
	private int[][] noteCheck = new int[2][16]; // [line][note]
	private int[][] indexCheck = new int[2][16]; // [line][index]
	private int lyrics_ptr = 0, hint_ptr = 0, key_ptr = 0, check_ptr = 0,
			line = 0, hintType = 0, songNo = 0;
	private boolean isEnd = false, isFinnished = false;

	private Button btnDo, btnRe, btnMi, btnFa, btnSol, btnLa, btnTi, btnDoH, btnBack;
	private MediaPlayer mpDo, mpRe, mpMi, mpFa, mpSol, mpLa, mpTi, mpDoH;
	private TextView[][] txtLyrics = new TextView[2][16];
	private TextView[][] txtHints = new TextView[2][16];

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piano);
		res = getResources();

		// initialize the MediaPlayers for each note
		mpDo = MediaPlayer.create(this, R.raw.note_do);
		mpRe = MediaPlayer.create(this, R.raw.note_re);
		mpMi = MediaPlayer.create(this, R.raw.note_mi);
		mpFa = MediaPlayer.create(this, R.raw.note_fa);
		mpSol = MediaPlayer.create(this, R.raw.note_sol);
		mpLa = MediaPlayer.create(this, R.raw.note_la);
		mpTi = MediaPlayer.create(this, R.raw.note_ti);
		mpDoH = MediaPlayer.create(this, R.raw.note_doh);

		// bind Button widgets
		btnDo = (Button) findViewById(R.id.btnDo);
		btnRe = (Button) findViewById(R.id.btnRe);
		btnMi = (Button) findViewById(R.id.btnMi);
		btnFa = (Button) findViewById(R.id.btnFa);
		btnSol = (Button) findViewById(R.id.btnSo);
		btnLa = (Button) findViewById(R.id.btnLa);
		btnTi = (Button) findViewById(R.id.btnTi);
		btnDoH = (Button) findViewById(R.id.btnDoH);
		btnBack = (Button) findViewById(R.id.btnBack);

		// int[] tArray = res.getIntArray(R.array.txtLyrics0);
		// int len = tArray.length;
		// for (int i = 0; i < len; i++) {
		// txtLyrics[0][i] = (TextView) findViewById(tArray[i]);
		// }
		//
		// tArray = res.getIntArray(R.array.txtLyrics1);
		// len = tArray.length;
		// for (int i = 0; i < len; i++) {
		// txtLyrics[1][i] = (TextView) findViewById(tArray[i]);
		// }
		//
		// tArray = res.getIntArray(R.array.txtHints0);
		// len = tArray.length;
		// for (int i = 0; i < len; i++) {
		// txtHints[0][i] = (TextView) findViewById(tArray[i]);
		// }
		//
		// tArray = res.getIntArray(R.array.txtHints1);
		// len = tArray.length;
		// for (int i = 0; i < len; i++) {
		// txtHints[1][i] = (TextView) findViewById(tArray[i]);
		// }

		// bind TextView widgets
		txtLyrics[0][0] = (TextView) findViewById(R.id.txtLyrics00);
		txtLyrics[0][1] = (TextView) findViewById(R.id.txtLyrics01);
		txtLyrics[0][2] = (TextView) findViewById(R.id.txtLyrics02);
		txtLyrics[0][3] = (TextView) findViewById(R.id.txtLyrics03);
		txtLyrics[0][4] = (TextView) findViewById(R.id.txtLyrics04);
		txtLyrics[0][5] = (TextView) findViewById(R.id.txtLyrics05);
		txtLyrics[0][6] = (TextView) findViewById(R.id.txtLyrics06);
		txtLyrics[0][7] = (TextView) findViewById(R.id.txtLyrics07);
		txtLyrics[0][8] = (TextView) findViewById(R.id.txtLyrics08);
		txtLyrics[0][9] = (TextView) findViewById(R.id.txtLyrics09);
		txtLyrics[0][10] = (TextView) findViewById(R.id.txtLyrics0A);
		txtLyrics[0][11] = (TextView) findViewById(R.id.txtLyrics0B);
		txtLyrics[0][12] = (TextView) findViewById(R.id.txtLyrics0C);
		txtLyrics[0][13] = (TextView) findViewById(R.id.txtLyrics0D);
		txtLyrics[0][14] = (TextView) findViewById(R.id.txtLyrics0E);
		txtLyrics[0][15] = (TextView) findViewById(R.id.txtLyrics0F);
		txtLyrics[1][0] = (TextView) findViewById(R.id.txtLyrics10);
		txtLyrics[1][1] = (TextView) findViewById(R.id.txtLyrics11);
		txtLyrics[1][2] = (TextView) findViewById(R.id.txtLyrics12);
		txtLyrics[1][3] = (TextView) findViewById(R.id.txtLyrics13);
		txtLyrics[1][4] = (TextView) findViewById(R.id.txtLyrics14);
		txtLyrics[1][5] = (TextView) findViewById(R.id.txtLyrics15);
		txtLyrics[1][6] = (TextView) findViewById(R.id.txtLyrics16);
		txtLyrics[1][7] = (TextView) findViewById(R.id.txtLyrics17);
		txtLyrics[1][8] = (TextView) findViewById(R.id.txtLyrics18);
		txtLyrics[1][9] = (TextView) findViewById(R.id.txtLyrics19);
		txtLyrics[1][10] = (TextView) findViewById(R.id.txtLyrics1A);
		txtLyrics[1][11] = (TextView) findViewById(R.id.txtLyrics1B);
		txtLyrics[1][12] = (TextView) findViewById(R.id.txtLyrics1C);
		txtLyrics[1][13] = (TextView) findViewById(R.id.txtLyrics1D);
		txtLyrics[1][14] = (TextView) findViewById(R.id.txtLyrics1E);
		txtLyrics[1][15] = (TextView) findViewById(R.id.txtLyrics1F);
		txtHints[0][0] = (TextView) findViewById(R.id.txtHint00);
		txtHints[0][1] = (TextView) findViewById(R.id.txtHint01);
		txtHints[0][2] = (TextView) findViewById(R.id.txtHint02);
		txtHints[0][3] = (TextView) findViewById(R.id.txtHint03);
		txtHints[0][4] = (TextView) findViewById(R.id.txtHint04);
		txtHints[0][5] = (TextView) findViewById(R.id.txtHint05);
		txtHints[0][6] = (TextView) findViewById(R.id.txtHint06);
		txtHints[0][7] = (TextView) findViewById(R.id.txtHint07);
		txtHints[0][8] = (TextView) findViewById(R.id.txtHint08);
		txtHints[0][9] = (TextView) findViewById(R.id.txtHint09);
		txtHints[0][10] = (TextView) findViewById(R.id.txtHint0A);
		txtHints[0][11] = (TextView) findViewById(R.id.txtHint0B);
		txtHints[0][12] = (TextView) findViewById(R.id.txtHint0C);
		txtHints[0][13] = (TextView) findViewById(R.id.txtHint0D);
		txtHints[0][14] = (TextView) findViewById(R.id.txtHint0E);
		txtHints[0][15] = (TextView) findViewById(R.id.txtHint0F);
		txtHints[1][0] = (TextView) findViewById(R.id.txtHint10);
		txtHints[1][1] = (TextView) findViewById(R.id.txtHint11);
		txtHints[1][2] = (TextView) findViewById(R.id.txtHint12);
		txtHints[1][3] = (TextView) findViewById(R.id.txtHint13);
		txtHints[1][4] = (TextView) findViewById(R.id.txtHint14);
		txtHints[1][5] = (TextView) findViewById(R.id.txtHint15);
		txtHints[1][6] = (TextView) findViewById(R.id.txtHint16);
		txtHints[1][7] = (TextView) findViewById(R.id.txtHint17);
		txtHints[1][8] = (TextView) findViewById(R.id.txtHint18);
		txtHints[1][9] = (TextView) findViewById(R.id.txtHint19);
		txtHints[1][10] = (TextView) findViewById(R.id.txtHint1A);
		txtHints[1][11] = (TextView) findViewById(R.id.txtHint1B);
		txtHints[1][12] = (TextView) findViewById(R.id.txtHint1C);
		txtHints[1][13] = (TextView) findViewById(R.id.txtHint1D);
		txtHints[1][14] = (TextView) findViewById(R.id.txtHint1E);
		txtHints[1][15] = (TextView) findViewById(R.id.txtHint1F);

		lyrics[0] = res.getStringArray(R.array.song0_lyrics);
		lyrics[1] = res.getStringArray(R.array.song1_lyrics);
		lyrics[2] = res.getStringArray(R.array.song2_lyrics);
		lyrics[3] = res.getStringArray(R.array.song3_lyrics);
		lyrics[4] = res.getStringArray(R.array.song4_lyrics);
		
		hints[0] = res.getIntArray(R.array.song0_notes);
		hints[1] = res.getIntArray(R.array.song1_notes);
		hints[2] = res.getIntArray(R.array.song2_notes);
		hints[3] = res.getIntArray(R.array.song3_notes);
		hints[4] = res.getIntArray(R.array.song4_notes);
		
		final Spinner songList = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_entry, res.getStringArray(R.array.song_list));
		aAdapter.setDropDownViewResource(R.layout.spinner_entry);
		songList.setAdapter(aAdapter);
		
		songList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				songNo = songList.getSelectedItemPosition();
				initialize();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				songNo = Spinner.INVALID_POSITION;
				initialize();
			}
		});
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				backToMain();
			}
		});
		
		btnDo.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpDo, event, 0);
				return false;
			}
		});

		btnRe.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpRe, event, 1);
				return false;
			}
		});

		btnMi.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpMi, event, 2);
				return false;
			}
		});

		btnFa.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpFa, event, 3);
				return false;
			}
		});

		btnSol.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpSol, event, 4);
				return false;
			}
		});

		btnLa.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpLa, event, 5);
				return false;
			}
		});

		btnTi.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpTi, event, 6);
				return false;
			}
		});

		btnDoH.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mpStart(mpDoH, event, 7);
				return false;
			}
		});
	}
	
	private void backToMain() {
		startActivity(new Intent("com.cookbook.MainMenuActivity"));
		this.finish();
	}
	
	private void initialize() {
		lyrics_ptr = 0;
		hint_ptr = 0;
		check_ptr = 0;
		line = 0;
		isEnd = false;
		isFinnished = false;
		
		// reset all colors
		for (int i = 0; i <= 15; i++) {
			txtHints[0][i].setBackgroundColor(res.getColor(R.color.transparent));
			txtHints[1][i].setBackgroundColor(res.getColor(R.color.transparent));
		}
		
		if (songNo != Spinner.INVALID_POSITION) {
			loadNotes();
			loadNotes();
		} else {
			for (int i = 0; i <= 15; i++) {
				txtHints[line][i].setText("");
				txtLyrics[line][i].setText("");
			}
		}
	}

	private void loadNotes() {
		int i = 0;
		key_ptr = 0;

		// load the lyrics and hints on the TextViews
		do {
			if (lyrics[songNo][lyrics_ptr].equals("_")) {
				txtLyrics[line][i].setText(" ");
				txtHints[line][i].setText("");
			} else if (lyrics[songNo][lyrics_ptr].equals("+")) {
				if (lyrics[songNo][lyrics_ptr + 1].equals("=")) {
					// to signify the end of the song
					noteCheck[line][key_ptr + 1] = 9;
					isEnd = true;
				}
				break;
			} else {
				noteCheck[line][key_ptr] = hints[songNo][hint_ptr]; // save note
				indexCheck[line][key_ptr] = i; // save index
				txtLyrics[line][i].setText(lyrics[songNo][lyrics_ptr]);
				txtHints[line][i]
						.setText(hintStrings[hintType][hints[songNo][hint_ptr]]);
				key_ptr++;
				hint_ptr++;
			}
			txtHints[line][i].setBackgroundColor(res
					.getColor(R.color.transparent));
			lyrics_ptr++;
			i++;
		} while (i <= 15);

		// clear the text of the rest of the TextViews
		while (i <= 15) {
			txtHints[line][i].setText("");
			txtLyrics[line][i].setText("");
			i++;
		}

		noteCheck[line][key_ptr] = 8; // to signify end of line
		line = (line + 1) % 2; // to toggle between 0 and 1
		lyrics_ptr++;
		// start in the next line
		txtHints[line][0].setBackgroundColor(res.getColor(R.color.dark_yellow));
	}

	private void checkNote(int key) {
		if (key == noteCheck[line][check_ptr]) {
			txtHints[line][indexCheck[line][check_ptr]].setBackgroundColor(res
					.getColor(R.color.dark_green));
			check_ptr++;
			txtHints[line][indexCheck[line][check_ptr]].setBackgroundColor(res
					.getColor(R.color.dark_yellow));
		} else {
			txtHints[line][indexCheck[line][check_ptr]].setBackgroundColor(res
					.getColor(R.color.dark_red));
		}

		if (noteCheck[line][check_ptr] == 8) {
			if (!isEnd) // check if the end of the song has been reached
				loadNotes();
			else {
				if (noteCheck[line][check_ptr + 1] == 9) {
					txtHints[line][0].setBackgroundColor(res
							.getColor(R.color.dark_green));
					isFinnished = true;
				} else {
					txtHints[line][0].setBackgroundColor(res
							.getColor(R.color.dark_green));
					// to toggle between 0 and 1 for the last time
					line = (line + 1) % 2;
					txtHints[line][0].setBackgroundColor(res
							.getColor(R.color.dark_yellow));
				}
			}
			check_ptr = 0;
		}
	}

	private void mpStart(MediaPlayer mp, MotionEvent event, int key) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			if (!isFinnished)
				checkNote(key);
			mp.start();
		} else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
			mp.pause();
			mp.seekTo(0);
		}
	}

}
