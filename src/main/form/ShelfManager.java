package main.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.form.Common.Mode;
import main.item.Book;
import main.item.BookShelf;
import main.item.Cd;
import main.item.CdShelf;
import main.item.Production;
import main.item.Shelf;

/*
 * 本棚管理クラス
 */
public class ShelfManager extends JPanel {

	private ShelfMain shelfMain = null; // メインクラス
	private Shelf[] shelf = { new BookShelf(), new CdShelf() };

	private JTextField[] bookTexts = null; // 入力欄
	private JTextField[] cdTexts = null; // 入力欄
	private JTabbedPane tabPane = null; // タブパネル

	private DefaultTableModel bookModel = null; //情報作成
	private DefaultTableModel cdModel = null; //情報作成

	private DefaultTableModel[] model = {bookModel, cdModel};

	private JTable bookTable = null; // テーブル作成
	private JTable cdTable = null; // テーブル作成

	private JTable[] table = { bookTable, cdTable };

	private int tabPaneIndex = 0;

	private int ID_ROW_NUMBER = 3;

	/*
	 * コンストラクタ
	 *
	 * @param mainFrame メインフレーム情報
	 */
	public ShelfManager(ShelfMain mainFrame) {
		shelfMain = mainFrame;

	}

	/*
	 * モード設定
	 *
	 * @param mode 選択したメニュー情報
	 */
	public void SetMode(Mode mode) {
		// 全ての要素削除
		this.removeAll();

		// メニューへ戻る
		JButton btn = new JButton("メニューへ戻る");
		btn.setBounds(400, 14, 150, 30);
		btn.addActionListener(new ActionListener() {
			// メニューボタンイベント
			public void actionPerformed(ActionEvent e) {
				// メイン画面遷移
				TopMenu();
				tabPaneIndex = 0;
			}
		});
		this.add(btn);

		// 画面作成
		JLabel paneltitle = null;
		setLayout(null);
		paneltitle = new JLabel(mode.getName() + "画面");
		paneltitle.setBounds(250, 10, 243, 39);
		paneltitle.setFont(new Font("Meiryo UI", Font.BOLD, 30));
		paneltitle.setForeground(Color.white);
		this.add(paneltitle);
		switch (mode) {
		case DISP: // 表示画面
			CreateDispDeleteForm(mode);
			viewShelf();
			this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
			this.add(tabPane);
			break;

		case REGiST: // 登録画面
			CreateRegistForm();
			this.setBackground(Color.getHSBColor(0.66f, 0.5f, 0.8f));
			this.add(tabPane);
			break;

		case DELETE: // 削除画面
			CreateDispDeleteForm(mode);
			viewShelf();
			this.setBackground(Color.getHSBColor(0.33f, 0.5f, 0.8f));
			this.add(tabPane);
			break;

		default: // 定義外
			// 本来であれば、エラーメッセージを通知など。
			break;
		}
	}

	/*
	 * 表示・削除画面作成
	 *
	 * @param mode 選択したメニュー情報
	 */
	private void CreateDispDeleteForm(Mode mode) {
		// タブが既に存在していれば削除
		if (tabPane != null) {
			this.remove(tabPane);
		}

		// タブ準備
		tabPane = new JTabbedPane();
		tabPane.setBounds(10, 50, 600, 300);

		// 削除の場合に削除ボタン追加
		if (mode == main.form.Common.Mode.DELETE) {
			JButton allDeleteBtn = new JButton("全て削除");
			allDeleteBtn.setBounds(440, 380, 150, 40);

			allDeleteBtn.addActionListener(new ActionListener() {
				// 削除ボタンイベント
				public void actionPerformed(ActionEvent e) {

					int option = JOptionPane.showConfirmDialog(null, "本当に削除しますか？", "", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						// 全削除
						if (deleteAll()) {
							JOptionPane.showMessageDialog(null, "全削除しました。");

							// 再描画
							SetMode(main.form.Common.Mode.DELETE);
						} else {
							JOptionPane.showMessageDialog(null, "削除に失敗しました。");
						}
					}

				}
			});

			JButton selectsDeleteBtn = new JButton("選択削除");
			selectsDeleteBtn.setBounds(280, 380, 150, 40);

			selectsDeleteBtn.addActionListener(new ActionListener() {
				// 削除ボタンイベント
				public void actionPerformed(ActionEvent e) {

					int option = JOptionPane.showConfirmDialog(null, "本当に削除しますか？", "", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						// 選択削除
						if (deleteOne()) {
							JOptionPane.showMessageDialog(null, "選択した行を削除しました。");

							// 再描画
							SetMode(main.form.Common.Mode.DELETE);
						} else {
							JOptionPane.showMessageDialog(null, "削除に失敗しました。");
						}
					}

				}
			});

			this.add(allDeleteBtn);
			this.add(selectsDeleteBtn);
			this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
		}
	}

	/*
	 * 登録画面フォーム作成
	 */
	private void CreateRegistForm() {

		// タブが既に存在していれば削除
		if (tabPane != null) {
			this.remove(tabPane);
		}

		// タブ
		tabPane = new JTabbedPane();

		// パネル
		JPanel bookTabPanel = new JPanel();
		JPanel cdTabPanel = new JPanel();

		// 入力項目
		JLabel[] bookLabels = new JLabel[2];
		bookLabels[0] = new JLabel(Book.TITLE_CAPTION); // 題名
		bookLabels[1] = new JLabel(Book.CREATER_CAPTION); // 作者

		JLabel[] cdLabels = new JLabel[2];
		cdLabels[0] = new JLabel(Cd.SONG_CAPTION); // 曲名
		cdLabels[1] = new JLabel(Cd.SINGER_CAPTION); // 歌手

		// 入力欄
		bookTexts = new JTextField[2];
		bookTexts[0] = new JTextField("", 0);
		bookTexts[1] = new JTextField("", 0);

		cdTexts = new JTextField[2];
		cdTexts[0] = new JTextField("", 0);
		cdTexts[1] = new JTextField("", 0);

		// 登録ボタン
		JButton bookTabBtn = new JButton("登録");
		bookTabBtn.addActionListener(new ActionListener() {
			// 登録ボタンイベント
			public void actionPerformed(ActionEvent e) {

				// 本の登録処理
				if (add()) {
					JOptionPane.showMessageDialog(null, "登録しました。");
				} else {
					JOptionPane.showMessageDialog(null, "登録に失敗しました。");
				}

			}
		});

		// 本棚用グループレイアウト
		GroupLayout bookLayout = new GroupLayout(bookTabPanel);
		bookLayout.setAutoCreateGaps(true);
		bookLayout.setAutoCreateContainerGaps(true);

		// 題名と作者をグルーピング設定
		GroupLayout.SequentialGroup bookHGroup = bookLayout.createSequentialGroup();
		bookHGroup.addGroup(bookLayout.createParallelGroup()
				.addComponent(bookLabels[0]).addComponent(bookLabels[1]));
		bookHGroup.addGroup(bookLayout.createParallelGroup()
				.addComponent(bookTexts[0]).addComponent(bookTexts[1]));
		bookHGroup.addGroup(bookLayout.createParallelGroup()
				.addComponent(bookTabBtn));
		bookLayout.setHorizontalGroup(bookHGroup);

		GroupLayout.SequentialGroup bookVGroup = bookLayout.createSequentialGroup();
		bookVGroup.addGroup(bookLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(bookLabels[0]).addComponent(bookTexts[0]));
		bookVGroup.addGroup(bookLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(bookLabels[1]).addComponent(bookTexts[1]));
		bookVGroup.addGroup(bookLayout.createParallelGroup(Alignment.CENTER)
				.addComponent(bookTabBtn));
		bookLayout.setVerticalGroup(bookVGroup);

		bookTabPanel.setLayout(bookLayout);

		tabPane.addTab("本", bookTabPanel);
		tabPane.setBounds(35, 50, 550, 150);

		// 登録ボタン
		JButton cdTabBtn = new JButton("登録");
		cdTabBtn.addActionListener(new ActionListener() {
			// 登録ボタンイベント
			public void actionPerformed(ActionEvent e) {

				// CDの登録処理
				if (add()) {
					JOptionPane.showMessageDialog(null, "登録しました。");
				} else {
					JOptionPane.showMessageDialog(null, "登録に失敗しました。");
				}

			}
		});

		// CD棚用グループレイアウト
		GroupLayout cdLayout = new GroupLayout(cdTabPanel);
		cdLayout.setAutoCreateGaps(true);
		cdLayout.setAutoCreateContainerGaps(true);

		// 題名と作者をグルーピング設定
		GroupLayout.SequentialGroup cdHGroup = cdLayout.createSequentialGroup();
		cdHGroup.addGroup(cdLayout.createParallelGroup()
				.addComponent(cdLabels[0]).addComponent(cdLabels[1]));
		cdHGroup.addGroup(cdLayout.createParallelGroup()
				.addComponent(cdTexts[0]).addComponent(cdTexts[1]));
		cdHGroup.addGroup(cdLayout.createParallelGroup()
				.addComponent(cdTabBtn));
		cdLayout.setHorizontalGroup(cdHGroup);

		GroupLayout.SequentialGroup cdVGroup = cdLayout.createSequentialGroup();
		cdVGroup.addGroup(cdLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(cdLabels[0]).addComponent(cdTexts[0]));
		cdVGroup.addGroup(cdLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(cdLabels[1]).addComponent(cdTexts[1]));
		cdVGroup.addGroup(cdLayout.createParallelGroup(Alignment.CENTER)
				.addComponent(cdTabBtn));
		cdLayout.setVerticalGroup(cdVGroup);

		cdTabPanel.setLayout(cdLayout);

		// タブ追加
		tabPane.addTab("CD", cdTabPanel);

	}

	/*
	 * メイン画面へ遷移
	 */
	public void TopMenu() {
		shelfMain.changePanel();
	}

	/*
	 * 追加
	 */
	private boolean add() {
		tabPaneIndex = tabPane.getSelectedIndex();
		// 本のタブが選択されている場合
		if (tabPaneIndex == 0) {
			// 題名、作者の入力チェック
			boolean inputChk = true;
			if (bookTexts[0].getText().length() > Book.TITLE_LENGTH) {
				JOptionPane.showMessageDialog(null,
						"題名は" + String.valueOf(Book.TITLE_LENGTH) + "文字以内で入力してください。");
				inputChk = false;
			} else if (bookTexts[0].getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "題名が未入力です。");
				inputChk = false;
			}

			if (bookTexts[1].getText().length() > Book.CREATER_LENGTH) {
				JOptionPane.showMessageDialog(null,
						"作者は" + String.valueOf(Book.CREATER_LENGTH) + "文字以内で入力してください。");
				inputChk = false;
			} else if (bookTexts[1].getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "作者が未入力です。");
				inputChk = false;
			}
			if (inputChk) {
				// 本の生成
				Book book = new Book();
				if (book.setTitle(bookTexts[0].getText()) && book.setPerson(bookTexts[1].getText())) {
					// 本棚への登録
					if (shelf[0].addData(book.getTitle(), book.getPerson())) {
						return true;
					} else {
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"登録に失敗しました。未入力の項目もしくは、" +
									"題名は" + String.valueOf(Book.TITLE_LENGTH) + "文字以内" +
									"作者は" + String.valueOf(Book.CREATER_LENGTH) + "文字以内で入力してください。");
				}
			} else {
				return false;
			}
			return false;
		}

		if (tabPaneIndex == 1) {
			// 歌手、曲名の入力チェック
			boolean inputChk = true;
			if (cdTexts[0].getText().length() > Cd.SONG_LENGTH) {
				JOptionPane.showMessageDialog(null,
						"曲名は" + String.valueOf(Cd.SONG_LENGTH) + "文字以内で入力してください。");
				inputChk = false;
			} else if (cdTexts[0].getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "曲名が未入力です。");
				inputChk = false;
			}

			if (cdTexts[1].getText().length() > Cd.SINGER_LENGTH) {
				JOptionPane.showMessageDialog(null,
						"歌手は" + String.valueOf(Cd.SINGER_LENGTH) + "文字以内で入力してください。");
				inputChk = false;
			} else if (cdTexts[1].getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "歌手が未入力です。");
				inputChk = false;
			}

			if (inputChk) {
				// CDの生成
				Cd cd = new Cd();

				if (cd.setTitle(cdTexts[0].getText()) && cd.setPerson(cdTexts[1].getText())) {
					// 棚への登録
					if (shelf[1].addData(cd.getTitle(), cd.getPerson())) {
						return true;
					} else {
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"登録に失敗しました。未入力の項目もしくは、" +
									"曲名は" + String.valueOf(Cd.SONG_LENGTH) + "文字以内" +
									"歌手は" + String.valueOf(Cd.SINGER_LENGTH) + "文字以内で入力してください。");
				}
			} else {
				return false;
			}
		}
		return false;
	}

	/*
	 * 全削除
	 */
	private boolean deleteAll() {
		tabPaneIndex = tabPane.getSelectedIndex();
		// 選択されているパネルによって削除する対象を変える。
		if (shelf[tabPaneIndex].deleteAllData()) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "削除できる" + shelf[tabPaneIndex].getText() + "はありません。");
			return false;
		}
	}

	/*
	 * 一部削除
	 */
	private boolean deleteOne() {
		tabPaneIndex = tabPane.getSelectedIndex();
		int index = table[tabPaneIndex].getSelectedRow();
		String id = "";

		if (index == -1) {
			JOptionPane.showMessageDialog(null, "削除したい" + shelf[tabPaneIndex].getText() + "を選択してください。");
			return false;
		}

		id = model[tabPaneIndex].getValueAt(index, ID_ROW_NUMBER).toString();

		// 設定されているIDを引数に削除する。
		if (shelf[tabPaneIndex].deleteData(id)) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "削除できる" + shelf[tabPaneIndex].getText() + "はありません。");
			return false;
		}
	}

	/*
	 * 棚表示
	 */
	private void viewShelf() {

		// テーブルの列名及び設定用の値作成
		String[] bookColumnNames = { "No", "題名", "作者", "ID" };
		// 登録されている本の数
		int bookCount = shelf[0].countData();
		String bookTableData[][] = new String[bookCount][bookColumnNames.length];

		// DBから本棚のデータを取得
		Production[] books = shelf[0].getData(bookCount);
		for (int cnt = 0; cnt < bookCount; cnt++) {
			bookTableData[cnt][0] = String.valueOf(cnt + 1); // No
			bookTableData[cnt][1] = books[cnt].getTitle(); // 題名
			bookTableData[cnt][2] = books[cnt].getPerson(); // 作者
			// 表示はしないが、idを削除用に設定する
			bookTableData[cnt][3] = books[cnt].getId(); // ID
		}

		// 情報作成
		model[0] = new DefaultTableModel(bookTableData, bookColumnNames);
		// テーブル作成
		table[0] = new JTable(model[0]);
		table[0].setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		// IDを設定するが、テーブルには表示しない
		table[0].removeColumn(table[0].getColumn("ID"));
		// スクロールパネルに設定してタブに追加
		JScrollPane bookSp = new JScrollPane(table[0]);
		tabPane.add(Book.NAME, bookSp);

		// テーブルの列名及び設定用の値作成
		String[] cdColumnNames = { "No", "曲名", "歌手", "ID" };
		int cdCount = 0;
		Production[] cds = null;
		cdCount = shelf[1].countData();
		String cdTableData[][] = new String[cdCount][cdColumnNames.length];

		// DBからCD棚のデータを取得
		cds = shelf[1].getData(cdCount);
		for (int cnt = 0; cnt < cdCount; cnt++) {
			cdTableData[cnt][0] = String.valueOf(cnt + 1); // No
			cdTableData[cnt][1] = cds[cnt].getTitle(); // 曲名
			cdTableData[cnt][2] = cds[cnt].getPerson(); // 歌手
			// 表示はしないが、IDを削除用に設定する
			cdTableData[cnt][3] = cds[cnt].getId(); // ID
		}

		// 情報作成
		model[1] = new DefaultTableModel(cdTableData, cdColumnNames);
		// テーブル作成
		table[1] = new JTable(model[1]);
		table[1].setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		// IDを設定するが、テーブルには表示しない
		table[1].removeColumn(table[1].getColumn("ID"));
		// スクロールパネルに設定してタブに追加
		JScrollPane cdSp = new JScrollPane(table[1]);
		tabPane.add(Cd.NAME, cdSp);

		tabPane.setSelectedIndex(tabPaneIndex);
	}
}
