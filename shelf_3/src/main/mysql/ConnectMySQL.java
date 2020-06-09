package main.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.item.Book;
import main.item.Cd;
import main.item.Production;

public class ConnectMySQL {
	static final String URL = "jdbc:mysql://localhost:3306/shelf?useSSL=false&characterEncoding=UTF-8&serverTimezone=JST";
	static final String USERNAME = "root";
	static final String PASSWORD = "tech2020";

	/*
	 * データを棚に登録するメソッド
	 */
	public static boolean insertData(String sql) {
		boolean connect_ok = false;
		try {
			//データベース接続
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//データ登録
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			//データベースのクローズ
			connection.close();
			connect_ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect_ok;
	}

	/*
	 * データを棚から削除するメソッド
	 */
	public static boolean deleteData(String sql) {
		boolean connect_ok = false;
		try {
			//データベース接続
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//データ登録
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			//データベースのクローズ
			connection.close();
			connect_ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect_ok;
	}

	/*
	 * データを棚から削除するメソッド
	 */
	public static boolean deleteAllData(String sql) {
		boolean connect_ok = false;
		try {
			//データベース接続
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//データ登録
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			//データベースのクローズ
			connection.close();
			connect_ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect_ok;
	}

	/*
	 * 表示用データを取得
	 */
	public static List<Production> getBookData(String sql) {
		try {
			//データベース接続
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//データ選択
			PreparedStatement ps = connection.prepareStatement(sql);
			// クエリーを実行して結果セットを取得
			ResultSet rs = ps.executeQuery();
			// 検索された行数分ループ
			List<Production> bookList = new ArrayList<>();
			while (rs.next()) { //最初は空行なので初回nextしてデータ行へ移る。
				Book book = new Book();
				// titleデータを取得
				String title = rs.getString("title");
				book.setTitle(title);
				// personデータを取得
				String author = rs.getString("author");
				book.setPerson(author);
				// idデータを取得
				String id = rs.getString("id");
				book.setId(id);
				// データの表示
				System.out.println("title;" + " " + title + ", author;" + " " + author);
				bookList.add(book);
			}
			//データベースのクローズ
			connection.close();
			return bookList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 表示用データを取得
	 */
	public static List<Production> getCdData(String sql) {
		try {
			//データベース接続
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//データ選択
			PreparedStatement ps = connection.prepareStatement(sql);
			// クエリーを実行して結果セットを取得
			ResultSet rs = ps.executeQuery();
			// 検索された行数分ループ
			List<Production> cdList = new ArrayList<>();
			while (rs.next()) { //最初は空行なので初回nextしてデータ行へ移る。
				Cd cd = new Cd();
				// titleデータを取得
				String song = rs.getString("song");
				cd.setTitle(song);
				// personデータを取得
				String singer = rs.getString("singer");
				cd.setPerson(singer);
				// idデータを取得
				String id = rs.getString("id");
				cd.setId(id);
				// データの表示
				System.out.println("song;" + " " + song + ", singer;" + " " + singer);
				cdList.add(cd);
			}
			//データベースのクローズ
			connection.close();
			return cdList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 棚の中身の登録数を取得するメソッド
	 */
	public static int countData(String sql) {
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			int data_size = 0;
			// データ行へ進める
			if (rs.next()) {
				// 個数取得
				String count = rs.getString("CNT");
				data_size = Integer.parseInt(count);
			}
			statement.close();
			//データベースのクローズ
			connection.close();
			return data_size;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
