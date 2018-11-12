/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { DataModelMappingComponent } from 'app/entities/data-model-mapping/data-model-mapping.component';
import { DataModelMappingService } from 'app/entities/data-model-mapping/data-model-mapping.service';
import { DataModelMapping } from 'app/shared/model/data-model-mapping.model';

describe('Component Tests', () => {
    describe('DataModelMapping Management Component', () => {
        let comp: DataModelMappingComponent;
        let fixture: ComponentFixture<DataModelMappingComponent>;
        let service: DataModelMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataModelMappingComponent],
                providers: []
            })
                .overrideTemplate(DataModelMappingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataModelMappingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataModelMappingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DataModelMapping(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dataModelMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
