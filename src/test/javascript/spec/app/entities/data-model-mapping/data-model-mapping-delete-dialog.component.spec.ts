/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyAppTestModule } from '../../../test.module';
import { DataModelMappingDeleteDialogComponent } from 'app/entities/data-model-mapping/data-model-mapping-delete-dialog.component';
import { DataModelMappingService } from 'app/entities/data-model-mapping/data-model-mapping.service';

describe('Component Tests', () => {
    describe('DataModelMapping Management Delete Component', () => {
        let comp: DataModelMappingDeleteDialogComponent;
        let fixture: ComponentFixture<DataModelMappingDeleteDialogComponent>;
        let service: DataModelMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataModelMappingDeleteDialogComponent]
            })
                .overrideTemplate(DataModelMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataModelMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataModelMappingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
