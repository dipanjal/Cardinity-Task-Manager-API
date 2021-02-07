package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.mappers.UserDetailObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;
import rest.api.cardinity.taskmanager.repository.UserDetailRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Service
@RequiredArgsConstructor
public class CardinityUserDetailService extends BaseService implements UserDetailsService {

    private final UserDetailRepository userDetailRepository;
    protected final UserDetailObjectMapper mapper;

    @Transactional(readOnly = true)
    public Response<List<CardinityUserDetailModel>> getAllUsers() {
        List<UserDetailEntity> userDetailEntities = userDetailRepository.getAll();
        if(CollectionUtils.isEmpty(userDetailEntities))
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No user found");
        return ResponseUtils.createSuccessResponse(mapper.mapToUserDetailModel(userDetailEntities));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserDetailEntity> opt = userDetailRepository.getByUserNameOpt(userName);
        if(opt.isEmpty())
            throw new UsernameNotFoundException(userName +" User cannot be found");
        return mapper.mapToUserModel(opt.get());
    }
}
