package org.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private static String filename;
    int currentid = 0;
    private static Connection con;

    public JdbcSudokuBoardDao(String filename) {
        this.filename = filename;
            con = connect();
    }

    public static Connection connect() {
        try {
            String url = "jdbc:sqlite:C:\\Users\\Marcin\\IdeaProjects\\mka_pn_1200_04\\connect\\" + filename;
            con = DriverManager.getConnection(url);
            con.setAutoCommit(false);
            DatabaseMetaData meta = con.getMetaData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    @Override
    public SudokuBoard read() {
        ResultSet rs = null;
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);


        String sql1 = "SELECT rowid "
                + "FROM sudokuIndex WHERE name == ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql1);) {
            pstmt.setString(1,filename);
            rs  = pstmt.executeQuery();
            currentid = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        String sql2 = String.format("SELECT xpos,ypos,content "
                + "FROM sudokuContent WHERE boardId == %1$s",currentid);
        try (Statement stmt = con.createStatement()) {
            rs = stmt.executeQuery(sql2);

            for (int i = 0; i < 82; i++) {
                sudokuBoard.setBoard(rs.getInt("xpos"),
                        rs.getInt("ypos"), rs.getInt("content"));
                rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj)  {
        String table1 = "CREATE TABLE IF NOT EXISTS sudokuIndex (\n"
                + "\tname text NOT NULL UNIQUE\n"
                +
                ");";
        String table2 = "CREATE TABLE IF NOT EXISTS sudokuContent (\n"
                + "\tboardId integer,\n"
                + "\txpos INTEGER NOT NULL,\n"
                + "\typos INTEGER NOT NULL,\n"
                + "\tcontent INTEGER NOT NULL,\n"
                + "    FOREIGN KEY (boardId)\n"
                + " "
                + "       REFERENCES sudokuIndex (rowid)\n"
                + "            ON DELETE CASCADE\n"
                + "            ON UPDATE NO ACTION\n"
                + ");";

        try (Statement stmt = con.createStatement()) {
            stmt.execute(table1);
            con.commit();
            stmt.execute(table2);
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        // save index
        String sql1 = "INSERT OR REPLACE INTO sudokuIndex(name) VALUES(?)";
        try (PreparedStatement prstmt = con.prepareStatement(sql1)) {
            prstmt.setString(1,filename);
            prstmt.executeUpdate();
            //wrzucone wszystko + try w środu fora

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //find index
        ResultSet rs = null;
        String sql2 = "SELECT rowid "
                + "FROM sudokuIndex WHERE name == ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql2)) {
            pstmt.setString(1,filename);
            rs  = pstmt.executeQuery();
            currentid = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String sql3 = "INSERT OR REPLACE INTO sudokuContent(boardId,xpos,ypos,content) VALUES(?,?,?,?)";
        try (PreparedStatement prstmt = con.prepareStatement(sql3)) {

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    try {
                        prstmt.setInt(1, currentid);
                        prstmt.setInt(2, i);
                        prstmt.setInt(3, j);
                        prstmt.setInt(4, (obj.getBoard(i,j).getFieldValue()));
                        prstmt.executeUpdate();
                    } catch (SQLException e) {
                        try {
                            con.rollback();
                            con.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // finish write
        try {
            con.commit();
            rs.close();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void saveBoards(List<SudokuBoard> list) throws ProblemWithFileException {

    }

    @Override
    public List<SudokuBoard> loadBoards() throws ProblemWithFileException {
        return null;
    }

}
