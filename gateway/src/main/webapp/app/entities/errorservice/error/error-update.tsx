import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './error.reducer';
import { IError } from 'app/shared/model/errorservice/error.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IErrorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ErrorUpdate = (props: IErrorUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { errorEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/error' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    if (errors.length === 0) {
      const entity = {
        ...errorEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayApp.errorserviceError.home.createOrEditLabel">Create or edit a Error</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : errorEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="error-id">ID</Label>
                  <AvInput id="error-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="exceptionNameLabel" for="error-exceptionName">
                  Exception Name
                </Label>
                <AvField
                  id="error-exceptionName"
                  type="text"
                  name="exceptionName"
                  validate={{
                    maxLength: { value: 500, errorMessage: 'This field cannot be longer than 500 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="error-message">
                  Message
                </Label>
                <AvField
                  id="error-message"
                  type="text"
                  name="message"
                  validate={{
                    maxLength: { value: 500, errorMessage: 'This field cannot be longer than 500 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="stackTraceLabel" for="error-stackTrace">
                  Stack Trace
                </Label>
                <AvField
                  id="error-stackTrace"
                  type="text"
                  name="stackTrace"
                  validate={{
                    maxLength: { value: 5000, errorMessage: 'This field cannot be longer than 5000 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdAtLabel" for="error-createdAt">
                  Created At
                </Label>
                <AvInput
                  id="error-createdAt"
                  type="datetime-local"
                  className="form-control"
                  name="createdAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.errorEntity.createdAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="serviceNameLabel" for="error-serviceName">
                  Service Name
                </Label>
                <AvField
                  id="error-serviceName"
                  type="text"
                  name="serviceName"
                  validate={{
                    maxLength: { value: 200, errorMessage: 'This field cannot be longer than 200 characters.' },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/error" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  errorEntity: storeState.error.entity,
  loading: storeState.error.loading,
  updating: storeState.error.updating,
  updateSuccess: storeState.error.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ErrorUpdate);
