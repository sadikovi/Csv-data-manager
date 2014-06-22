Csv-data-manager
================

CSV loader for Oracle BIEE 11g


1. Deploy .war file on Weblogic server using Weblogic Console.
2. Configure database source using Weblogic Console
Should match parameters described below:
    public static final String JNDIPath = "jdbc/rtk-csv";
    public static final String JNDIEnv = "java:comp/env";
    private static final String DBSource = "jdbc:oracle:thin:@192.168.2.165:1521/DWHQA";
    private static final String Username = "LOGISTICS";
    private static final String Password = "LOGISTICS";
3. Copy configuration file to directory: 
    /u01/oracle/weblogic/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/
4. Paste code in import.html on the dashboard page
5. Enjoy loading CSV files

Parameters used in configuration file:
    * biServerUrl - must be written as @Scheme@://@ServerName@:@ServerPort@ (!!! without /analytics/)
    * for instance: http://msk-02-orabits.tsretail.ru:9704
        
		* xml for csv load
		* every import template consists of "import" tag with parameters
		* id - must be unique
		* filterName - required
		* roles - for users to use this template (required)
		* columnsCount - required
		* startRow - start row in template (required)
		* separator - separator to distinguish values in a row (required)
		* 
		* procedure which will be performed - "load" tag, use it with one parameter inspite of how many columns you have in a template
		* beforeload - procedure that will be run before "load" tag text (f.i. clear source table)
		* afterload - procedure that will be run after "load" tag text (f.i. next loading step -> other tables load)
		*
		* charset - is not used right now
