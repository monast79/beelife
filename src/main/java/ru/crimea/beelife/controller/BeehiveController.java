package ru.crimea.beelife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.crimea.beelife.dto.ApiaryDto;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.dto.BeehiveWeightDto;
import ru.crimea.beelife.dto.ChartPeriod;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.service.ApiaryService;
import ru.crimea.beelife.service.BeehiveService;
import ru.crimea.beelife.service.BeehiveWeightService;

import java.util.*;

@Controller
public class BeehiveController {

    @Autowired
    private BeehiveService beehiveService;
    @Autowired
    private ApiaryService apiaryService;

    @Autowired
    private BeehiveWeightService beehiveWeightService;

    @GetMapping("/user/home/apiary/{id}")
    public String getBeehives(@PathVariable("id") Long apiaryId,
                              Authentication authentication,
                              Model model,
                              @RequestParam(required = false) String keyword,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "id,asc") String[] sort) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortField);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));


        try {
            Long userId = ((User) authentication.getPrincipal()).getId();
            ApiaryDto apiary = apiaryService.findById(apiaryId);
            Page<BeehiveDto> apiaryPage = beehiveService.getBeehivesByApiaryId(apiaryId, pageable, keyword);

            if (keyword != null) {
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("apiaryPage", apiaryPage);

            model.addAttribute("beehives", apiaryPage.getContent());
            model.addAttribute("currentPage", apiaryPage.getNumber() + 1);
            model.addAttribute("totalItems", apiaryPage.getTotalElements());
            model.addAttribute("totalPages", apiaryPage.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("apiaries", apiaryService.getApiariesByUserId(userId));
            model.addAttribute("userId", userId);
            model.addAttribute("apiary", apiary);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "userHome";
    }

    @GetMapping("/user/home/beehive/{id}")
    @ResponseBody
    public BeehiveDto getBeehive(@PathVariable("id") Long beehiveId) throws PermissionDeniedException {
        return beehiveService.findById(beehiveId);
    }

    @PostMapping("/user/home/beehive/save")
    public String saveApiary(@ModelAttribute BeehiveDto beehiveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            //errors processing
        }
        BeehiveDto beehiveDto = beehiveService.saveBeehive(beehiveForm);
        if (beehiveDto == null) {
            redirectAttributes.addFlashAttribute("message", "Can not save to DB");
        }
        return "redirect:/user/home/apiary/" + beehiveForm.getApiaryId();
    }

    @GetMapping("/user/home/beehive/delete/{id}")
    public String handleDeleteBeehive(@PathVariable("id") Long beehiveId, RedirectAttributes redirectAttributes) {
        Long apiaryId = null;
        try {
            apiaryId = beehiveService.findById(beehiveId).getApiaryId();
            beehiveService.deleteById(beehiveId);
            redirectAttributes.addFlashAttribute("message", "The Beehive with id=" + beehiveId + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/user/home/apiary/" + apiaryId;
    }

    @GetMapping("/user/home/beehive/details/{id}")
    public String getBeehiveDetails(@PathVariable("id") Long beehiveId,
                                    Authentication authentication,
                                    Model model,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    @RequestParam(defaultValue = "id,asc") String[] sort,
                                    @RequestParam( defaultValue = "month") String period) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortField);
        ChartPeriod chart = ChartPeriod.valueOf(period.toUpperCase());
        Date fromDate = getMinMeasureDate(chart);


        try {
            BeehiveDto beehive = beehiveService.findById(beehiveId);

            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

            Long userId = ((User) authentication.getPrincipal()).getId();
            List<BeehiveWeightDto> beehiveWeightDtos = beehiveWeightService.getAllByBeehiveId(beehiveId, fromDate);
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<BeehiveWeightDto> list;
            if (beehiveWeightDtos.size() < startItem) {
                list = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, beehiveWeightDtos.size());
                list = beehiveWeightDtos.subList(startItem, toIndex);
            }
            Page<BeehiveWeightDto> beehivePage  = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), beehiveWeightDtos.size());new PageImpl<>(list, PageRequest.of(currentPage, pageSize), beehiveWeightDtos.size());
            model.addAttribute("beehivePage", beehivePage);
            model.addAttribute("beehiveWeights", beehivePage.getContent());
            model.addAttribute("currentPage", beehivePage.getNumber() + 1);
            model.addAttribute("totalItems", beehivePage.getTotalElements());
            model.addAttribute("totalPages", beehivePage.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("apiaries", apiaryService.getApiariesByUserId(userId));
            model.addAttribute("userId", userId);
            model.addAttribute("beehive", beehive);
            model.addAttribute("chart", chart);
            setBeehiveChat(model, beehiveWeightDtos);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "userHome";
    }

    private void setBeehiveChat(Model model, List<BeehiveWeightDto> beehiveWeights) {
        Map<Date, Double> graphData = new TreeMap<>();
        beehiveWeights.forEach(beehiveWeight -> {
            graphData.put(beehiveWeight.getMeasure(), beehiveWeight.getWeight());
        });
        model.addAttribute("chartData", graphData);
        ;
    }

    private static Date getMinMeasureDate(ChartPeriod graphFilter) {
        Date currentDate = new Date();
        Date startDate = null;
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        if (graphFilter.WEEK.equals(graphFilter)) {
            c.add(Calendar.DAY_OF_WEEK, -1);
            startDate = c.getTime();
        } else if (graphFilter.MONTH.equals(graphFilter)) {
            c.add(Calendar.MONTH, -1);
            startDate = c.getTime();
        } else {
            c.set(Calendar.DAY_OF_YEAR, 1);
            startDate = c.getTime();
        }
        return startDate;
    }
}
