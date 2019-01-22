package dwz.business.website.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.beans.WebPage;
import com.irongteng.persistence.beans.WebWebsite;
import com.irongteng.persistence.mapper.WebPageMapper;
import com.irongteng.persistence.mapper.WebWebsiteMapper;

import dwz.business.enums.PageTarget;
import dwz.business.website.Page;
import dwz.business.website.Template;
import dwz.business.website.Website;
import dwz.business.website.WebsiteServiceMgr;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(WebsiteServiceMgr.SERVICE_NAME)
public class WebsiteServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements
    WebsiteServiceMgr {
    
    @Autowired
    private WebWebsiteMapper websiteDao;
    @Autowired
    private WebPageMapper webPageDao;


    @Override
    public String createPage(Page page) throws ServiceException {
        String ret = null;

        if (page == null || page.getTitle() == null) {
            log.debug("page is null.");
            throw new ServiceException("page is null.");
        }
        
        page.setTarget(page.getPageTarget());
        webPageDao.insert(page.getPersistentObject());

        return ret;
    }

    @Override
    public void deletePage(int id) {
        webPageDao.delete(id);
    }

    @Override
    public Page getPage(int id) {

        WebPage po = webPageDao.load(id);
        if (po != null) {
            return new Page(po);
        }

        return null;
    }

    @Override
    public List<Page> getPages() {
        ArrayList<Page> bos = new ArrayList<Page>();
        List<WebPage> pos = webPageDao.findAll();

        if (pos != null && pos.size() > 0) {
            for (WebPage po : pos) {
                bos.add(new Page(po));
            }
        }

        return bos;
    }

    @Override
    public List<Page> getPages(PageTarget target) {
        ArrayList<Page> bos = new ArrayList<Page>();
        List<WebPage> pos = webPageDao.findByTarget(target.toString());

        if (pos != null && pos.size() > 0) {
            for (WebPage po : pos) {
                bos.add(new Page(po));
            }
        }

        return bos;
    }
    
    @Override
    public void updatePage(Page page) {
        page.setTarget(page.getPageTarget());
        webPageDao.updateSelective(page.getPersistentObject());
    }

    @Override
    public Website getWebsite() {
        List<WebWebsite> pos = websiteDao.findAll();
        if (pos != null && pos.size() > 0) {
            return new Website(pos.iterator().next());
        }

        return new Website();
    }

    @Override
    public void saveWebsite(Website bo) throws ServiceException {
        if (bo == null) {
            log.debug("Website is null.");
            throw new ServiceException("Website is null.");
        }

        if (websiteDao.countAll() < 1) {
            websiteDao.insert(bo.getPersistentObject());
        } else {
            websiteDao.updateSelective(bo.getPersistentObject());
        }
    }

    @Override
    public Collection<Template> getTemplates() {
        return this.getAppConfig().getWebTemplateMap().values();
    }

    @Override
    public Template getDefaultTemplate() {
        return this.getTemplateByName("template1");
    }

    @Override
    public Template getTemplateByName(String templateName) {
        return this.getAppConfig().getWebTemplateMap().get(templateName);
    }

}
