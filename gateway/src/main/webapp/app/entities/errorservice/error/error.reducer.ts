import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IError, defaultValue } from 'app/shared/model/errorservice/error.model';

export const ACTION_TYPES = {
  FETCH_ERROR_LIST: 'error/FETCH_ERROR_LIST',
  FETCH_ERROR: 'error/FETCH_ERROR',
  CREATE_ERROR: 'error/CREATE_ERROR',
  UPDATE_ERROR: 'error/UPDATE_ERROR',
  DELETE_ERROR: 'error/DELETE_ERROR',
  RESET: 'error/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IError>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ErrorState = Readonly<typeof initialState>;

// Reducer

export default (state: ErrorState = initialState, action): ErrorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ERROR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ERROR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ERROR):
    case REQUEST(ACTION_TYPES.UPDATE_ERROR):
    case REQUEST(ACTION_TYPES.DELETE_ERROR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ERROR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ERROR):
    case FAILURE(ACTION_TYPES.CREATE_ERROR):
    case FAILURE(ACTION_TYPES.UPDATE_ERROR):
    case FAILURE(ACTION_TYPES.DELETE_ERROR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ERROR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ERROR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ERROR):
    case SUCCESS(ACTION_TYPES.UPDATE_ERROR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ERROR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'services/errorservice/api/errors';

// Actions

export const getEntities: ICrudGetAllAction<IError> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ERROR_LIST,
    payload: axios.get<IError>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IError> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ERROR,
    payload: axios.get<IError>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IError> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ERROR,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IError> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ERROR,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IError> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ERROR,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
