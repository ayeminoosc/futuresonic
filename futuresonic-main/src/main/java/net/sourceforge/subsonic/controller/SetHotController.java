/*
 This file is part of Subsonic.

 Subsonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Subsonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Subsonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2009 (C) Sindre Mehus
 */
package net.sourceforge.subsonic.controller;

import net.sourceforge.subsonic.domain.*;
import net.sourceforge.subsonic.service.*;
import net.sourceforge.subsonic.util.*;
import net.sourceforge.subsonic.filter.ParameterDecodingFilter;
import org.springframework.web.bind.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.*;
import org.springframework.web.servlet.view.*;

import javax.servlet.http.*;

/**
 * Controller for updating music file ratings.
 *
 * @author Madevil
 */
public class SetHotController extends AbstractController {

    private HotService hotService;
    private SecurityService securityService;
    private MediaFileService mediaFileService;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
    	Integer id = ServletRequestUtils.getIntParameter(request, "id");    	
    	Integer flag = ServletRequestUtils.getIntParameter(request, "flag");
    	
        MediaFile mediaFile = mediaFileService.getMediaFile(id);
        String username = securityService.getCurrentUsername(request);
        
        if (flag == 1){
            hotService.setAlbumHotFlag(username, mediaFile);
        }
        else
        {
            hotService.deleteAlbumHotFlag(mediaFile);
        }

        String url = "main.view?id=" + id;

        return new ModelAndView(new RedirectView(url));
    }

    public void setHotService(HotService hotService) {
        this.hotService = hotService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setMediaFileService(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }
}
