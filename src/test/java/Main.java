import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.flashsale.dao.ProductDAO;
import com.flashsale.entity.Product;

public class Main {
	public static void main(String[] args) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		ProductDAO mapper = session.getMapper(ProductDAO.class);
		List<Product> ps = mapper.queryAllProducts();
		for (Product product : ps) {
			System.out.println(product.toString());
		}
	}
}
