import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './address.reducer';
import { IAddress } from 'app/shared/model/employeeservice/address.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAddressDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AddressDetail = (props: IAddressDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { addressEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Address [<b>{addressEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="addressType">Address Type</span>
          </dt>
          <dd>{addressEntity.addressType}</dd>
          <dt>
            <span id="street">Street</span>
          </dt>
          <dd>{addressEntity.street}</dd>
          <dt>
            <span id="postalCode">Postal Code</span>
          </dt>
          <dd>{addressEntity.postalCode}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="country">Country</span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="stateProvince">State Province</span>
          </dt>
          <dd>{addressEntity.stateProvince}</dd>
          <dt>
            <span id="buildingNumber">Building Number</span>
          </dt>
          <dd>{addressEntity.buildingNumber}</dd>
          <dt>
            <span id="flatNumber">Flat Number</span>
          </dt>
          <dd>{addressEntity.flatNumber}</dd>
          <dt>Employee</dt>
          <dd>{addressEntity.employeeId ? addressEntity.employeeId : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ address }: IRootState) => ({
  addressEntity: address.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AddressDetail);
