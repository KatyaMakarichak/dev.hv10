package ua.homework.time;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String tz = req.getParameter("timezone");
        if (tz != null && !tz.isEmpty()) {
            String tzFixed = tz.replace(" ", "");
            boolean valid = Arrays.asList(TimeZone.getAvailableIDs()).contains(tzFixed.replace("UTC", "GMT"));
            if (!valid) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("<h1>Invalid timezone</h1>");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
