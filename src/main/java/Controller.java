import static java.lang.Integer.parseInt;
import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {


    public static void main(String [] args) throws IOException {

        StoreDAO dao = new StoreDAO();
        MustacheTemplateEngine mustache = new MustacheTemplateEngine();

        initData(dao);

        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

        staticFiles.location("/static");

        get("/", (req, res) -> {

            HashMap map = new HashMap();
            map.put("products", dao.getProducts());
            map.put("count", dao.getProducts().size());

            return new ModelAndView(map, "index.mustache");
        }, mustache);

        get("/category/:cat", (req, res) -> {

            HashMap map = new HashMap();
            String category = req.params(":cat");
            map.put("products", dao.getProductsInCategory(category));
            map.put("count", dao.getProducts().size());

            return new ModelAndView(map, "index.mustache");
        }, mustache);



        get("/product/:id", (req, res) -> {
            int id = parseInt(req.params(":id"));

            HashMap map = new HashMap();
            map.put("product", dao.getProduct(id));

            return new ModelAndView(map, "product.mustache");

        }, mustache);

        get("/cart", (req, res) -> {
            HashMap map = new HashMap();
            ArrayList<CartEntry> cart = dao.getCart(req.session().id());
            /* compute the cart total */
            double total = 0;
            for(CartEntry entry: cart) {
                total += entry.getCost();
            }
            map.put("cart", cart);
            map.put("total", total);

            return new ModelAndView(map, "cart.mustache");
        }, mustache);


        post("/cart", (req, res) -> {

            int productid = parseInt(req.queryParams("product"));
            int quantity = parseInt(req.queryParams("quantity"));

            dao.addToCart(req.session().id(), productid, quantity);

            res.redirect("/cart");
            return null;

        }, mustache);

    }


    private static void initData(StoreDAO dao) throws IOException {
        String csvFile = "apparel.csv";
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        int id = 0;
        String row = csvReader.readLine();  /* skip header line */
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if (!data[1].equals("")) {
                Product p = new Product(id++,
                        data[1],  /* name */
                        data[2],  /* description */
                        data[24], /* imageUrl */
                        data[5],  /* category */
                        100,
                        86.0 + id * 5
                );
                dao.addProduct(p);
            }
        }
    }
}

/*
    - about adding persistance and app structure...
  https://www.javaworld.com/article/3019792/jump-into-java-microframeworks-part-3-spark.html
 */