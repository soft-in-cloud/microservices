import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './error.reducer';
import { IError } from 'app/shared/model/errorservice/error.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IErrorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ErrorDetail = (props: IErrorDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { errorEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Error [<b>{errorEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="exceptionName">Exception Name</span>
          </dt>
          <dd>{errorEntity.exceptionName}</dd>
          <dt>
            <span id="message">Message</span>
          </dt>
          <dd>{errorEntity.message}</dd>
          <dt>
            <span id="stackTrace">Stack Trace</span>
          </dt>
          <dd>{errorEntity.stackTrace}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{errorEntity.createdAt ? <TextFormat value={errorEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="serviceName">Service Name</span>
          </dt>
          <dd>{errorEntity.serviceName}</dd>
        </dl>
        <Button tag={Link} to="/error" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/error/${errorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ error }: IRootState) => ({
  errorEntity: error.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ErrorDetail);
