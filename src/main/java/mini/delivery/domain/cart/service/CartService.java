package mini.delivery.domain.cart.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.cart.entity.Cart;
import mini.delivery.domain.cart.entity.CartItem;
import mini.delivery.domain.cart.repository.CartItemRepository;
import mini.delivery.domain.cart.repository.CartRepository;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.domain.menu.repository.MenuRepository;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    /**
     * 1. 장바구니를 조회하고 없으면 생성 (orElseGet)
     * 2. 다른 가게 메뉴를 담을 경우, 장바구니에 담긴 기존 메뉴 삭제 (가게 비교)
     * 3. 동일한 메뉴 추가 불가
     * 4. CartItem 저장
     */
    @Transactional
    public void addCartItem(Long menuId, int quantity, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Menu menu = menuRepository.findByIdWithStore(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(Cart.create(user, menu.getStore())));

        clearCartIfDifferentStore(cart, menu.getStore());
        validateDuplicateMenu(cart, menuId);

        CartItem cartItem = CartItem.create(cart, menu, quantity);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long itemId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        CartItem cartItem = cartItemRepository.findByIdAndUserId(itemId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
    }

    private void clearCartIfDifferentStore(Cart cart, Store newStore) {
        Long currentStoreId = cart.getStore().getId();
        Long newStoreId = newStore.getId();

        if (!currentStoreId.equals(newStoreId)) {
            cartItemRepository.deleteAllByCart(cart);
            cart.updateCart(newStore);
        }
    }

    private void validateDuplicateMenu(Cart cart, Long menuId) {
        cartItemRepository.findByCartAndMenuId(cart, menuId)
                .ifPresent(item -> {
                    throw new CustomException(ErrorCode.CART_ITEM_ALREADY_EXISTS);
                });
    }
}
