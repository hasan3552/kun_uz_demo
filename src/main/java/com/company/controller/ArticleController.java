package com.company.controller;

import com.company.dto.article.ArticleFilterDTO;
import com.company.dto.article.*;
import com.company.dto.attach.AttachDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.exp.BadRequestException;
import com.company.service.ArticleService;
import com.company.service.AttachService;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "Article Controller")
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
    @ApiOperation(value = "Article Create", notes = "Article create for admin")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ArticleCreateDTO dto) {
        //HttpServletRequest request,
        //@RequestHeader("Authorization") String jwt,
//        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        //     Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        log.info("Request for article create {}", dto);
        ArticleDTO articleDTO = articleService.create(dto);

        return ResponseEntity.ok(articleDTO);
    }

    @ApiOperation(value = "Article List", notes = "Article list for administration, no access users!")
    @GetMapping("/list")
    public ResponseEntity<?> getArticleListForAdministration() {
        //@RequestHeader("Authorization") String jwt
        // HttpServletRequest request

        // Integer profileId = JwtUtil.decode(jwt);
        // Integer profileId = HttpHeaderUtil.getId(request);
//
//        if (profileService.get(profileId).getRole().equals(ProfileRole.USER)) {
//            throw new BadRequestException("No access");
//        }
        log.info("Request for article lsit ");

        List<ArticleDTO> dtos = articleService.getArticleDTOList();
        return ResponseEntity.ok(dtos);
    }

    @ApiOperation(value = "Article List", notes = "Article list general")
    @GetMapping("/public/list")
    public ResponseEntity<?> getArticleList() {
        log.info("Request for article list general");
        List<ArticleDTO> dtos = articleService.getArticleDTOListForUsers();
        return ResponseEntity.ok(dtos);
    }

    //     3. Delete Article (MODERATOR)
    @ApiOperation(value = "Article Change Visible(DELETED MAPPING)", notes = "Article change status only MODERATOR")
    @DeleteMapping("/adm")
    public ResponseEntity<?> changeStatus(@RequestParam("id") String uuid
                                          // HttpServletRequest request
    ) {

        //  Integer moderatorId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);

        ArticleDTO dto = articleService.changeVisible(uuid);
        log.info("Request for change visible moderatorId");

        return ResponseEntity.ok(dto);
    }

    //     2. Update (Moderator (status to not publish))
    @ApiOperation(value = "Article update", notes = "Article update for moderator")
    @PutMapping("/adm")
    public ResponseEntity<?> update(//@RequestHeader("Authorization") String jwt,
                                    //   HttpServletRequest request,
                                    @RequestBody @Valid ArticleCreateDTO dto,
                                    @RequestParam("id") String uuid) {
//        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        log.info("Request for atricle update moderatorId");

        ArticleDTO update = articleService.update(dto, uuid);

        return ResponseEntity.ok(update);
    }


    //     4. Change status by id (PUBLISHER) (publish,not_publish)
    @ApiOperation(value = "Article change status", notes = "Article change status only super_moderator(public, not_public)")
    @PutMapping("/adm/change_status")
    public ResponseEntity<?> changeStatus(
            //HttpServletRequest request,
                                          @RequestBody @Valid ArticleChangeStatusDTO dto,
                                          @RequestParam("id") String uuid) {

       // Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.SUPER_MODERATOR);
        log.info("Request for change status super_moderator");

        ArticleDTO update = articleService.changeStatus(dto, uuid);

        return ResponseEntity.ok(update);

    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date
//            (Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi.)
//    ArticleShortInfo
//     6. Get Last 3 Article By Types  ordered_by_created_date
//            (Berilgan types bo'yicha oxirgi 3ta pubished bo'lgan article ni return qiladi.)
//    ArticleShortInfo
    @ApiOperation(value = "Article List By Type AND Limit", notes = "Give type key and article limit and get article list")
    @GetMapping("/public")
    public ResponseEntity<?> getArticleByType(@RequestParam("key") String key,
                                              @RequestParam("limit") Integer limit) {
        log.info("Request for article list by type key:{}, limit:{}", key, limit);
        List<ArticleShortDTO> dtos = articleService.getArticleDTOListByType(key, limit);
        return ResponseEntity.ok(dtos);
    }

    // 7. Get Last 8 Added articles witch id not included in given list.
//            ([1,2,3,])
//    ArticleShortInfo
    @ApiOperation(value = "Article List Last", notes = "Give size and get last article's list by size only status=public and visible = true")
    @GetMapping("/public/last")
    public ResponseEntity<?> getLastArticleList(@RequestParam("size") Integer size) {

        log.info("Request for article last  size:{}", size);
        List<ArticleShortDTO> dtos = articleService.getArticleDTOListLast(size);

        return ResponseEntity.ok(dtos);
    }


    //    8. Get Article By Id
//    ArticleFullInfo
    @ApiOperation(value = "Article Get By Id", notes = "Article get by id general method")
    @GetMapping("")
    public ResponseEntity<?> getArticleById(@RequestParam("id") String uuid) {
        log.info("Request for article id:{}", uuid);
        ArticleDTO articleDTO = articleService.getArticleDTOById(uuid);

        return ResponseEntity.ok(articleDTO);
    }

    //      15. Get Article By Category Key (Pagination)
//    ArticleShortInfo
    @ApiOperation(value = "Article List By Category Key", notes = "Give category key and size and page and get article list by size and category key")
    @GetMapping("/adm/category")
    public ResponseEntity<?> getArticleListByCategoryKey(@RequestParam("key") String categoryKey,
                                                         @RequestParam("size") Integer size,
                                                         @RequestParam("page") Integer page) {
        log.info("Request for article list category_key :{}, size:{}, page:{} ", categoryKey, size, page);
        PageImpl page1 = articleService.getArticleListByCategoryKey(categoryKey, size, page);

        return ResponseEntity.ok(page1);
    }

    @ApiOperation(value = "Article List Last 8", notes = "Article create for admin")
    @PostMapping("/public/last8")
    public ResponseEntity<?> getLast8NotIn(@RequestBody @Valid ArticleRequestDTO dto) {
        log.info("Request for article last 8");
        List<ArticleShortDTO> response = articleService.getLat8ArticleNotIn(dto.getIdList());
        return ResponseEntity.ok().body(response);
    }

    //     19. Increase Article View Count by Article Id
//            (article_id)
    @ApiOperation(value = "Add view count")
    @PutMapping("/public/view")
    public ResponseEntity<?> view(@RequestParam String uuid) {
        log.info("Request for article view dto:{}", uuid);
        ArticleDTO dto = articleService.view(uuid);

        return ResponseEntity.ok(dto);
    }

    //       10. Get 4 most read articles
//            ArticleShortInfo
    @ApiOperation(value = "Article Most Read By Amount", notes = "Give amount Get the most popular article by amount")
    @GetMapping("/public/most_read")
    public ResponseEntity<?> mostRead(@RequestParam("amount") Integer amount) {
        log.info("Request for article most read size:{}", amount);
        List<ArticleDTO> list = articleService.getMostReadByAmount(amount);

        return ResponseEntity.ok(list);
    }

    //    12. Get Last 5 Article By Types  And By Region Key
//            ArticleShortInfo
    @ApiOperation(value = "Article List Type And Region", notes = "Article list  general method")
    @GetMapping("/public/region_type")
    public ResponseEntity<?> getList5ByTypeAndRegion(@RequestParam("t_key") String tKey,
                                                     @RequestParam("r_key") String rKey) {
        log.info("Request for article by type_key:{} and region_key:{}", tKey, rKey);
        List<ArticleShortInfoByCategory> list = articleService.getList5ByTypeRegion(tKey, rKey);

        return ResponseEntity.ok(list);
    }
//    13. RegionController  80
//    16. ArticleLikeController    26
//    17. ArticleLikeController    37
//    18. ArticleLikeController    48

    //    14. Get Last 5 Article Category Key
//            ArticleShortInfo
    @ApiOperation(value = "Article 5List By CategoryKey", notes = "Article list  general method")
    @GetMapping("/public/category5")
    public ResponseEntity<?> getList5ByCategoryKey(@RequestParam("key") String key) {
        log.info("Request for article list by category key :{}", key);
        List<ArticleShortDTO> list = articleService.getList5ByCategory(key);

        return ResponseEntity.ok(list);
    }

//     11. Get Last 4 Article By TagName (Bitta article ni eng ohirida chiqib turadi.)
//    ArticleShortInfo

//    -   9. Get Last 4 Article By Types and except given article id.
//    ArticleShortInfo


    @ApiOperation(value = "Article Upload attach RequestParams(file, articleId)", notes = "Article update attach For administration")
    @PostMapping("/adm/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("articleId") String articleId
                                    //HttpServletRequest request
    ) {
        log.info("Request for article upload articleID:{}", articleId);
      //  Integer profileId = HttpHeaderUtil.getId(request);
        AttachDTO dto = attachService.saveToArticle(file, articleId);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Article Filter", notes = "Article list  general method")
    @PostMapping("/public/filter")
    public ResponseEntity<?> filter(@RequestBody ArticleFilterDTO dto) {
        log.info("Request for article filter by:{}", dto);
        List<ArticleShortDTO> response = articleService.filter(dto);
        return ResponseEntity.ok().body(response);
    }
}
