package com.vormetric.rest.azure_examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.vormetric.rest.helperclasses.VormetricCryptoServerHelper;
import com.vormetric.rest.helperclasses.VormetricCryptoServerSettings;

public class SQLAzureVormetricToken {

	// Connect to your database.
	// Replace server name, username, and password with your credentials
	public static void main(String[] args) throws Exception {

		String action = "tokenize";
		VormetricCryptoServerSettings vcs = new VormetricCryptoServerSettings();
		String sensitive = "673-56-5628";

		String connectionUrl = "jdbc:sqlserver://yourazuredb.database.windows.net:1433;database=wingtiptoys;user=yourid@yourazuredb;password=yourpwd;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

		String encssn = VormetricCryptoServerHelper.doTokenizeData(vcs.getvcstokenserver(), vcs.getvcsuserid(),
				vcs.getvcspassword(), vcs.getvcsTokengroup(), vcs.getvcsTokentemplate(), sensitive, action);

		String insertSql = "insert into creditscore values (?,?)";

		ResultSet resultSet = null;

		try (Connection connection = DriverManager.getConnection(connectionUrl);

				PreparedStatement prepsInsertCreditInfo = connection.prepareStatement(insertSql);) {

			connection.setAutoCommit(true);

			prepsInsertCreditInfo.setString(1, encssn);
			prepsInsertCreditInfo.setInt(2, 778);
			boolean returnvalue = prepsInsertCreditInfo.execute();

			System.out.println("completed insert with " + returnvalue);

		}
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}