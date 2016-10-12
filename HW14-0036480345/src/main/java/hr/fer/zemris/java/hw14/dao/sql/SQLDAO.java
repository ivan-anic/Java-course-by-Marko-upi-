package hr.fer.zemris.java.hw14.dao.sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOptions;

/**
 * An implementation of {@linkplain DAO} interface using SQL technology. This
 * implementation uses the connection provided by the
 * {@linkplain SQLConnectionProvider}.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SQLDAO implements DAO {

	/** The list of default voting definitions stored on the disk. */
	private static String[] votingDefinitions = { "voting-definition.txt", "voting-definition2.txt" };

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> entries = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll(rs.getLong(1),
								rs.getString(2),
								rs.getString(3));
						entries.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error during getting the polls list.", ex);
		}
		return entries;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						poll = new Poll(rs.getLong(1),
								rs.getString(2),
								rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error during getting the poll.", ex);
		}
		return poll;
	}

	@Override
	public List<PollOptions> getPollOptions(long id) throws DAOException {
		List<PollOptions> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement(
							"select id, optionTitle, optionLink, votesCount from PollOptions where pollID=? order by id");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOptions option = new PollOptions(
								rs.getLong(1),
								rs.getString(2),
								rs.getString(3),
								rs.getLong(4));
						options.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Error during getting the poll options list.", ex);
		}
		return options;
	}

	@Override
	public void createTablesIfInexistent(ServletContext sc) {

		DatabaseMetaData dbmd;
		try {
			dbmd = SQLConnectionProvider.getConnection().getMetaData();

			ResultSet rs = dbmd.getTables(null, null, "POLLS", null);
			if (!rs.next()) {
				createPoll("POLLS");
				insertPoll(1,
						"Favourite band poll:",
						"Among the following bands, which one is your favourite?");
				insertPoll(2,
						"Favourite chair poll:",
						"Among the following chairs, which one is your favourite?");
			}
			rs = dbmd.getTables(null, null, "POLLOPTIONS", null);
			if (!rs.next()) {
				createPoll("POLLOPTIONS");
				syncPollOptions(sc);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Creates the table specified by its title, availible tables are:
	 * <ul>
	 * <li>POLLS</li>
	 * <li>POLLOPTIONS</li>
	 * </ul>
	 * 
	 * @param title
	 *            the title
	 */
	private void createPoll(String title) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			if (title.equals("POLLS")) {
				pst = con
						.prepareStatement(
								"CREATE TABLE Polls" +
										"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
										"title VARCHAR(150) NOT NULL," +
										"message CLOB(2048) NOT NULL)");
			} else if (title.equals("POLLOPTIONS")) {
				pst = con
						.prepareStatement(
								"CREATE TABLE PollOptions" +
										"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
										"optionTitle VARCHAR(100) NOT NULL," +
										"optionLink VARCHAR(150) NOT NULL," +
										"pollID BIGINT," +
										"votesCount BIGINT," +
										"FOREIGN KEY (pollID) REFERENCES Polls(id))");
			}
			try {
				pst.executeUpdate();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Error during creating tables.", ex);
		}
	}

	public void insertPoll(long id, String title, String message) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement(
							"INSERT INTO Polls (title, message) values (?,?)");
			pst.setString(1, title);
			pst.setString(2, message);
			try {
				pst.executeUpdate();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error during poll insertion.", ex);
		}
	}

	/**
	 * Sync poll options, adding the default values from the text files located
	 * on the disk.
	 *
	 * @param sc
	 *            the {@linkplain ServletContext} instance
	 */
	public void syncPollOptions(ServletContext sc) {

		List<String> lines;
		String[] args;

		for (int i = 0; i < votingDefinitions.length; i++) {

			try {
				lines = Files.readAllLines(Paths.get(sc.getRealPath("/WEB-INF/" + votingDefinitions[i])));
			} catch (IOException e) {
				throw new DAOException("Invalid file structure");
			}

			for (String line : lines) {
				args = line.split("\t");
				insertPollOption(i + 1, args[1], args[2], 0);
			}
		}
	}

	@Override
	public void insertPollOption(long id, String optionTitle, String optionLink, long votesCount) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement(
							"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)");
			pst.setString(1, optionTitle);
			pst.setString(2, optionLink);
			pst.setLong(3, id);
			pst.setLong(4, votesCount);
			try {
				pst.executeUpdate();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error during polloption insertion.", ex);
		}
	}

	public Integer getOptionCount(long pollID, long optionID) {
		Integer data = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement(
							"select id, optionTitle, optionLink, votesCount from polloptions where pollID=? and id=?");
			pst.setLong(1, Long.valueOf(pollID));
			pst.setLong(2, optionID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						data = Integer.valueOf(rs.getInt(4));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Error during getting poll option vote count.", ex);
		}
		return data;
	}

	public void updateVoteCount(long pollID, long optionID, int votesCount) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("update polloptions set votesCount=? where id=? and pollID=?");
			pst.setLong(1, Integer.valueOf(votesCount));
			pst.setLong(2, Long.valueOf(optionID));
			pst.setLong(3, pollID);
			try {
				pst.executeUpdate();
			} catch (Exception ex) {
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Error during updating the poll option vote count", ex);
		}
	}
}