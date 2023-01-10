import java.sql.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;

public class MySQLJDBC{
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    
    String id;
    String title;
    String company;
    String releasedate; 
    String country;
    int totalscreen;
    double profit;
    BigDecimal bigprofit;
    int totalnum;
    String grade;
    
    Scanner scanner = new Scanner(System.in);
    
    public void createNinsert() throws Exception{
    	//create table
    	try {
    		File file = new File("C:\\Users\\username\\create_table.txt");
    		FileReader fr = new FileReader(file);
    		int i=0;
            int data = 0;
            char[] charArr= new char[500];
            while((data = fr.read()) != -1){
            	charArr[i]=(char)data;
            	i=i+1;
            }
            String str = String.valueOf(charArr);

            stmt = conn.createStatement();
	        stmt.executeUpdate(str);
	        System.out.println(str);            
	        fr.close();
	        System.out.println("성공: 릴레이션 생성");
        }
        catch(SQLException ex) {
            System.out.println("실패: 릴레이션 생성");
        }
    	catch (Exception e){
			System.out.println("실패: 릴레이션 생성");
		}
    	
    	//insert data
    	try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\username\\movie_data.txt"));
            String tuple;
            String[] value =null;
            
            stmt = conn.createStatement();
            while((tuple = br.readLine())!=null) { 
            	value = tuple.split("\\|");
                id = value[1];
                title = value[2];
                company = value[3];
                releasedate = value[4];
                country = value[5];
                totalscreen = Integer.parseInt(value[6]);
                profit = Double.parseDouble(value[7]);
                totalnum = Integer.parseInt(value[8]);
                grade = value[9];
                BigDecimal bigprofit = new BigDecimal(profit);
                
                String movieData = "insert into movie values ('"
    					+id +"', '" +title +"', '" +company+ "', '" +releasedate +"', '" 
                		+country +"', "	+totalscreen+", " +bigprofit +", " +totalnum +", '" +grade 
                		+"')";
                System.out.println(movieData);
                stmt.executeUpdate(movieData);
            }
            
            br.close();
            System.out.println("성공: 데이터 추가");
        }
    	catch(SQLException ex) {
            System.out.println("실패: 데이터 추가");
        }
    	catch (Exception e){
			System.out.println("실패: 데이터 추가");
		}
    }
    
    public void titleSearch() { 
        try {
        	System.out.print("영화 제목: ");
            String title = scanner.nextLine();
            
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery("SELECT * FROM movie WHERE title like '%" +title +"%'");
    
	        while(rs.next()) {
	        	id =rs.getString(1);
	        	title =rs.getString(2);
	        	company =rs.getString(3);
	        	releasedate =rs.getString(4);
                country =rs.getString(5);
                totalscreen = rs.getInt(6);
                profit = rs.getDouble(7);
                totalnum = rs.getInt(8);
                grade = rs.getString(9);
	        	BigDecimal bigprofit = new BigDecimal(rs.getDouble("profit"));
	        	
	        	System.out.println(
	        			"|"+id+"|"+title+"|"+company+"|"+releasedate+"|"+country+"|"
            			+totalscreen+"|"+bigprofit+"|"+totalnum+"|"+grade
            	);
	        }
	    }
        catch(SQLException ex) {
            System.out.println("실패: 제목 검색");
        }
    	catch (Exception e){
			System.out.println("실패: 제목 검색");
		}
 
    }
    
    public void totalnumSearch(){
    	try {
    		System.out.print("n보다 더 많은 관객수를 기록한 영화를 검색. n: ");
    		int totalnum = scanner.nextInt();
    		
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM movie WHERE totalnum>" +totalnum);
            
            while(rs.next()) {
	        	id =rs.getString(1);
	        	title =rs.getString(2);
	        	company =rs.getString(3);
	        	releasedate =rs.getString(4);
                country =rs.getString(5);
                totalscreen = rs.getInt(6);
                profit = rs.getDouble(7);
                totalnum = rs.getInt(8);
                grade = rs.getString(9);
	        	BigDecimal bigprofit = new BigDecimal(rs.getDouble("profit"));
	        	
	        	System.out.println(
	        			"|"+id+"|"+title+"|"+company+"|"+releasedate+"|"+country+"|"
            			+totalscreen+"|"+bigprofit+"|"+totalnum+"|"+grade
            	);
	        }
        }
    	catch(SQLException ex) {
            System.out.println("실패: 제목 검색");
        }
    	catch (Exception e){
			System.out.println("실패: 제목 검색");
		}
        
    }
    
    public void releasedateSearch(){
    	try{
    		System.out.print("검색 시작 날짜: ");
            String startDate = scanner.nextLine();
            		
            System.out.print("검색 종료 날짜: ");
            String endDate = scanner.nextLine();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM movie WHERE releasedate between"+ "'" +startDate +"' and '" +endDate +"'"+"ORDER BY releasedate");
            
            while(rs.next()) {
	        	id =rs.getString(1);
	        	title =rs.getString(2);
	        	company =rs.getString(3);
	        	releasedate =rs.getString(4);
                country =rs.getString(5);
                totalscreen = rs.getInt(6);
                profit = rs.getDouble(7);
                totalnum = rs.getInt(8);
                grade = rs.getString(9);
	        	BigDecimal bigprofit = new BigDecimal(rs.getDouble("profit"));
	        	
	        	System.out.println(
	        			"|"+id+"|"+title+"|"+company+"|"+releasedate+"|"+country+"|"
            			+totalscreen+"|"+bigprofit+"|"+totalnum+"|"+grade
            	);
	        }
        }
    	catch(SQLException ex) {
            System.out.println("실패: 관객수 검색");
        }
    	catch (Exception e){
			System.out.println("실패: 관객수 검색");
		}
    }

    public void movieSearchProgram() throws Exception{
        try {
        	int botton=0;  
            
        	do {  
	            System.out.println("========================================");
	            System.out.println("(0) 종료");
	            System.out.println("(1) 릴레이션 생성 및 데이터 추가");
	            System.out.println("(2) 제목을 이용한 검색");
	            System.out.println("(3) 관객수를 이용한 검색");
	            System.out.println("(4) 개봉일을 이용한 검색");
	            System.out.println("========================================");
	            System.out.print("원하는 번호를 입력하시오: ");
	            
	            botton = scanner.nextInt();
	            scanner.nextLine();
	            
	            switch(botton) {
    	            case 0 : 
    	                System.out.println("========================================");
    	                System.out.println("종료");
    	                break;
    	            
    	            case 1:
    	            	MySQLJDBC tmpCreateNinsert = new MySQLJDBC();
    	            	tmpCreateNinsert.createNinsert();
    	            	break;
    	            
    	            case 2:
    	            	MySQLJDBC tmpTitleSearch = new MySQLJDBC();
    	                tmpTitleSearch.titleSearch();
    	            	break;
    	            
    	            case 3:
    	            	MySQLJDBC tmpTotalnumSearch = new MySQLJDBC();
    	            	tmpTotalnumSearch.totalnumSearch();
    	            	break;
    	            	
    	            case 4:
    	            	MySQLJDBC tmpReleasedateSearch = new MySQLJDBC();
    	            	tmpReleasedateSearch.releasedateSearch();
    	            	break;	
    	            		
	    	        default : System.out.println("0~4까지의 숫자를 입력하시오");
	    	        break;
    	        }
	        }while(botton != 0);
        }
        catch (SQLException ex) {
            //Handle errors for JDBC
        	System.out.println("실패: 번호 입력");
            }
        catch (Exception e){
            //Handle errors for Class.forName
        	System.out.println("실패: 번호 입력");
            }
    }

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/movie?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "pw");
			
            MySQLJDBC tmpMovieSearchProgram = new MySQLJDBC();
            tmpMovieSearchProgram.movieSearchProgram();

            stmt.close();
            conn.close();
        }
        
        catch(SQLException ex) {
            System.out.println("실패: 메인 메소드");
        }
    	catch (Exception e){
			System.out.println("실패: 메인 메소드");
		}
    }
}