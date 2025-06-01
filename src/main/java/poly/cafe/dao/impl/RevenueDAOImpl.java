package poly.cafe.dao.impl;

import java.util.Date;
import java.util.List;
import poly.cafe.dao.RevenueDAO;
import poly.cafe.entity.Revenue.ByCategory;
import poly.cafe.entity.Revenue.ByUser;
import poly.cafe.util.XQuery;

public class RevenueDAOImpl implements RevenueDAO {

    @Override
    public List<ByCategory> getByCategory(Date begin, Date end) {
        String revenueByCategorySql
                = "SELECT category.Name AS Category, "
                + "   sum(detail.UnitPrice*detail.Quantity*(1-detail.Discount)) AS Revenue,"
                + "   sum(detail.Quantity) AS Quantity,"
                + "   min(detail.UnitPrice) AS MinPrice,"
                + "   max(detail.UnitPrice) AS MaxPrice,"
                + "   avg(detail.UnitPrice) AS AvgPrice "
                + "FROM SOF2042_BillDetails detail "
                + "   JOIN SOF2042_Drinks drink ON drink.Id=detail.DrinkId"
                + "   JOIN SOF2042_Categories category ON category.Id=drink.CategoryId"
                + "   JOIN SOF2042_Bills bill ON bill.Id=detail.BillId "
                + "WHERE bill.Status=1 "
                + "   AND bill.Checkout IS NOT NULL "
                + "   AND bill.Checkout BETWEEN ? AND ? "
                + "GROUP BY category.Name "
                + "ORDER BY Revenue DESC";
        return XQuery.getBeanList(ByCategory.class, revenueByCategorySql, begin, end);
    }

    @Override
    public List<ByUser> getByUser(Date begin, Date end) {
        String revenueByUserSql
                = "SELECT bill.Username AS [User], "
                + "   sum(detail.UnitPrice*detail.Quantity*(1-detail.Discount)) AS Revenue,"
                + "   count(DISTINCT detail.BillId) AS Quantity,"
                + "   min(bill.Checkin) AS FirstTime,"
                + "   max(bill.Checkin) AS LastTime "
                + "FROM SOF2042_BillDetails detail "
                + "   JOIN SOF2042_Bills bill ON bill.Id=detail.BillId "
                + "WHERE bill.Status=1 "
                + "   AND bill.Checkout IS NOT NULL "
                + "   AND bill.Checkout BETWEEN ? AND ? "
                + "GROUP BY bill.Username "
                + "ORDER BY Revenue DESC";
        return XQuery.getBeanList(ByUser.class, revenueByUserSql, begin, end);
    }
}
