package mini.delivery.domain.menu.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.menu.dto.MenuCreateRequestDto;
import mini.delivery.domain.menu.dto.MenuCreateResponseDto;
import mini.delivery.domain.menu.dto.MenuUpdateRequestDto;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.domain.menu.repository.MenuRepository;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.store.repository.StoreRepository;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MenuService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public MenuCreateResponseDto createMenu(Long storeId, MenuCreateRequestDto menuCreateRequestDto, String email){
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findByIdAndUserId(storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = Menu.create(
                store,
                menuCreateRequestDto.getName(),
                menuCreateRequestDto.getPrice()
        );
        Menu savedMenu = menuRepository.save(menu);

        return MenuCreateResponseDto.from(savedMenu);
    }

    @Transactional
    public void updateMenu(Long storeId, Long menuId, MenuUpdateRequestDto menuUpdateRequestDto, String email) {
        // email로 user 조회
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Menu menu = menuRepository.findByIdAndStoreIdAndUserId(menuId, storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        if (menuUpdateRequestDto.getName() != null) {
            menu.updateName(menuUpdateRequestDto.getName());
        }
        if (menuUpdateRequestDto.getPrice() != null) {
            menu.updatePrice(menuUpdateRequestDto.getPrice());
        }
    }

    @Transactional
    public void deleteMenu(Long storeId, Long menuId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Menu menu = menuRepository.findByIdAndStoreIdAndUserId(menuId, storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        if(!menu.getStore().getId().equals(storeId)){
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }
        menu.deleteMenu();
    }
}
