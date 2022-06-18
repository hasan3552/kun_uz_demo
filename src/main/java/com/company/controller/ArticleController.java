package com.company.controller;

import com.company.dto.article.*;
import com.company.dto.attach.AttachDTO;
import com.company.dto.type.TypeDTO;
import com.company.enums.ProfileRole;
import com.company.exp.BadRequestException;
import com.company.service.ArticleService;
import com.company.service.AttachService;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import com.sun.net.httpserver.spi.HttpServerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileService profileService;

    //    1. CREATE (Moderator) status(NotPublished)
    @PostMapping("/adm")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    //@RequestHeader("Authorization") String jwt,
                                    @RequestBody ArticleCreateDTO dto) {

//        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.create(dto, profileId);

        return ResponseEntity.ok(articleDTO);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getArticleListForAdministration(//@RequestHeader("Authorization") String jwt
                                                             HttpServletRequest request) {
        // Integer profileId = JwtUtil.decode(jwt);
        Integer profileId = HttpHeaderUtil.getId(request);

        if (profileService.get(profileId).getRole().equals(ProfileRole.USER)) {
            throw new BadRequestException("No access");
        }

        List<ArticleDTO> dtos = articleService.getArticleDTOList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/detail/list")
    public ResponseEntity<?> getArticleList() {

        List<ArticleDTO> dtos = articleService.getArticleDTOListForUsers();
        return ResponseEntity.ok(dtos);
    }

    //     3. Delete Article (MODERATOR)
    @DeleteMapping("/adm")
    public ResponseEntity<?> changeStatus(@RequestParam("id") String uuid,
                                          HttpServletRequest request) {

        HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);

        ArticleDTO dto = articleService.changeVisible(uuid);

        return ResponseEntity.ok(dto);
    }

    //     2. Update (Moderator (status to not publish))
    @PutMapping("/adm")
    public ResponseEntity<?> update(//@RequestHeader("Authorization") String jwt,
                                    HttpServletRequest request,
                                    @RequestBody ArticleCreateDTO dto,
                                    @RequestParam("id") String uuid) {
//        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);

        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);

        ArticleDTO update = articleService.update(dto, uuid, profileId);

        return ResponseEntity.ok(update);
    }


    //     4. Change status by id (PUBLISHER) (publish,not_publish)
    @PutMapping("/adm/change_status")
    public ResponseEntity<?> changeStatus(HttpServletRequest request,
                                          @RequestBody ArticleChangeStatusDTO dto,
                                          @RequestParam("id") String uuid) {

        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.SUPER_MODERATOR);

        ArticleDTO update = articleService.changeStatus(dto, uuid, profileId);

        return ResponseEntity.ok(update);

    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date
//            (Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi.)
//    ArticleShortInfo
//     6. Get Last 3 Article By Types  ordered_by_created_date
//            (Berilgan types bo'yicha oxirgi 3ta pubished bo'lgan article ni return qiladi.)
//    ArticleShortInfo
    @GetMapping("/public")
    public ResponseEntity<?> getArticleByType(@RequestParam("key") String key,
                                              @RequestParam("limit") Integer limit) {

        List<ArticleShortDTO> dtos = articleService.getArticleDTOListByType(key, limit);
        return ResponseEntity.ok(dtos);
    }

    // 7. Get Last 8 Added articles witch id not included in given list.
//            ([1,2,3,])
//    ArticleShortInfo
    @GetMapping("/public/last")
    public ResponseEntity<?> getLastArticleList(@RequestParam("size") Integer size) {

        List<ArticleShortDTO> dtos = articleService.getArticleDTOListLast(size);

        return ResponseEntity.ok(dtos);
    }


    //    8. Get Article By Id
//    ArticleFullInfo
    @GetMapping("")
    public ResponseEntity<?> getArticleById(@RequestParam("id") String uuid) {
        ArticleDTO articleDTO = articleService.getArticleDTOById(uuid);

        return ResponseEntity.ok(articleDTO);
    }

    //      15. Get Article By Category Key (Pagination)
//    ArticleShortInfo
    @GetMapping("/category")
    public ResponseEntity<?> getArticleListByCategoryKey(@RequestParam("key") String categoryKey,
                                                         @RequestParam("size") Integer size,
                                                         @RequestParam("page") Integer page) {

        PageImpl page1 = articleService.getArticleListByCategoryKey(categoryKey, size, page);

        return ResponseEntity.ok(page1);
    }

    @PostMapping("/last8")
    public ResponseEntity<?> getLast8NotIn(@RequestBody ArticleRequestDTO dto) {
        List<ArticleShortDTO> response = articleService.getLat8ArticleNotIn(dto.getIdList());
        return ResponseEntity.ok().body(response);
    }

    //     19. Increase Article View Count by Article Id
//            (article_id)
    @PutMapping("/view")
    public ResponseEntity<?> view(@RequestBody ArticleShortDTO dto1) {
        ArticleDTO dto = articleService.view(dto1.getUuid());

        return ResponseEntity.ok(dto);
    }

    //       10. Get 4 most read articles
//            ArticleShortInfo
    @GetMapping("/most_read")
    public ResponseEntity<?> mostRead(@RequestParam("amount") Integer amount) {
        List<ArticleDTO> list = articleService.getMostReadByAmount(amount);

        return ResponseEntity.ok(list);
    }

    //    12. Get Last 5 Article By Types  And By Region Key
//            ArticleShortInfo
    @GetMapping("/region_type")
    public ResponseEntity<?> getList5ByTypeAndRegion(@RequestParam("t_key") String tKey,
                                                     @RequestParam("r_key") String rKey) {

        List<ArticleShortInfoByCategory> list = articleService.getList5ByTypeRegion(tKey, rKey);

        return ResponseEntity.ok(list);
    }
//    13. RegionController  80
//    16. ArticleLikeController    26
//    17. ArticleLikeController    37
//    18. ArticleLikeController    48

//    14. Get Last 5 Article Category Key
//            ArticleShortInfo
    @GetMapping("/category5")
    public ResponseEntity<?> getList5ByCategoryKey(@RequestParam("key") String key) {

        List<ArticleShortDTO> list = articleService.getList5ByCategory(key);

        return ResponseEntity.ok(list);
    }

//     11. Get Last 4 Article By TagName (Bitta article ni eng ohirida chiqib turadi.)
//    ArticleShortInfo

//    -   9. Get Last 4 Article By Types and except given article id.
//    ArticleShortInfo


    @PostMapping("/adm/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("articleId") String articleId,
                                    HttpServletRequest request) {

        Integer profileId = HttpHeaderUtil.getId(request);
        AttachDTO dto = attachService.saveToArticle(file, profileId, articleId);
        return ResponseEntity.ok().body(dto);
    }

}
